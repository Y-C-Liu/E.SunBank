package com.example.esunbank.repository;

import com.example.esunbank.model.Post;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    @Procedure(procedureName = "create_post_with_user_update")
    void createPostWithUserUpdate(Long userId, String content, String image);

    List<Post> findByUserUserId(Long userId);
}
