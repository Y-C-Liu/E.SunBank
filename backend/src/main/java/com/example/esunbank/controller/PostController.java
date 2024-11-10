package com.example.esunbank.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.esunbank.model.Post;
import com.example.esunbank.service.PostService;
import com.example.esunbank.service.UserService;
import com.example.esunbank.request.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@Valid @RequestBody PostRequest postRequest) {
        try {
            if (postRequest.getUserId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID is required.");
            }

            // Escape content and image to prevent XSS attacks
            String escapedContent = HtmlUtils.htmlEscape(postRequest.getContent());
            String escapedImage = HtmlUtils.htmlEscape(postRequest.getImage());

            postService.createPostWithStoredProc(postRequest.getUserId(), escapedContent, escapedImage);
            return ResponseEntity.ok("Post created successfully.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating post: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable Long userId) {
        try {
            List<Post> posts = postService.getPostsByUserId(userId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> posts = (List<Post>) postService.getAllPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.findPostById(postId);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/{postId}")
    @Transactional
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        try {
            Optional<Post> existingPost = Optional.ofNullable(postService.findPostById(postId));
            if (existingPost.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            post.setPostId(postId);  // 确保更新的是相同的 postId
            post.setContent(HtmlUtils.htmlEscape(post.getContent()));  // 防止XSS攻擊
            post.setImage(HtmlUtils.htmlEscape(post.getImage()));      // 防止XSS攻擊
            Post updatedPost = postService.updatePost(postId, post);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{postId}")
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the post");
        }
    }
}

