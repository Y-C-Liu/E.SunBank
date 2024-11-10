package com.example.esunbank.service;

import com.example.esunbank.model.Post;
import com.example.esunbank.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void createPostWithStoredProc(Long userId, String content, String image) {
        try {
            postRepository.createPostWithUserUpdate(userId, content, image);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create post with stored procedure: " + e.getMessage(), e);
        }
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserUserId(userId);
    }

    public Post findPostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

//    public List<Post> getPostsByUserId(Long userId) {
//        return postRepository.findByUserUserId(userId);
//    }

    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long postId, Post updatedPost) throws Exception {
        Optional<Post> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setContent(updatedPost.getContent());
            post.setImage(updatedPost.getImage());
            // 更新其他必要的欄位
            return postRepository.save(post);
        } else {
            throw new Exception("Post not found");
        }
    }

    public void deletePost(Long postId) throws Exception {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
        } else {
            throw new Exception("Post not found");
        }
    }
}
