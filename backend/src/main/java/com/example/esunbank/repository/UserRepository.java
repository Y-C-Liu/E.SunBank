package com.example.esunbank.repository;

import com.example.esunbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    // 添加通过用户名查找用户的方法
    Optional<User> findByUserName(String userName);
    // 新增通过手机号查找用户的方法
    Optional<User> findByPhoneNumber(String phoneNumber);
}