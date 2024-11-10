package com.example.esunbank.service;

import com.example.esunbank.model.Comment;
import com.example.esunbank.model.Post;
import com.example.esunbank.model.User;
import com.example.esunbank.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void createComment(User user, Post post, String content) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public Comment addComment(Comment comment) {
        if (comment.getUser() == null || comment.getPost() == null) {
            throw new IllegalArgumentException("User and Post cannot be null");
        }
        try {
            comment.setCreatedAt(LocalDateTime.now());
            return commentRepository.save(comment);
        } catch (Exception e) {
            System.out.println("Error adding comment: " + e.getMessage());
            throw new RuntimeException("Failed to add comment", e);
        }
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPost_PostId(postId);
    }
}
