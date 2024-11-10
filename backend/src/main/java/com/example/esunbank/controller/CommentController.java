package com.example.esunbank.controller;

import com.example.esunbank.model.*;
import com.example.esunbank.service.*;
import com.example.esunbank.request.CommentRequest;
import com.example.esunbank.model.Comment;
import com.example.esunbank.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List; // 確保導入此包
@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


//    @PostMapping("/create")
//    public String createComment(@RequestBody CommentRequest commentRequest) {
//        try {
//            User user = userService.findUserById(commentRequest.getUserId());
//            Post post = postService.findPostById(commentRequest.getPostId());
//            commentService.createComment(user, post, commentRequest.getContent());
//            return "留言成功！";
//        } catch (Exception e) {
//            return "留言失敗：" + e.getMessage();
//        }
//    }



    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        try {
            // 需要确保 comment 对象的字段完整，例如 userId, postId, content
            Comment createdComment = commentService.addComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
