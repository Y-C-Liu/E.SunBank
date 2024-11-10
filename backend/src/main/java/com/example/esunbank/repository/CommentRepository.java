package com.example.esunbank.repository;

import com.example.esunbank.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_PostId(Long postId); // 使用嵌套屬性來匹配 Post 中的 postId
}
