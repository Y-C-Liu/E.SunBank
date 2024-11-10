// UserService.java
package com.example.esunbank.service;

import com.example.esunbank.model.User;
import com.example.esunbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // 新增 validateUserByPhoneNumber 方法
    public Optional<User> validateUserByPhoneNumber(String phoneNumber, String password) {
        // 从数据库中查找用户
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        // 检查用户是否存在并验证密码
        if (user.isPresent() && user.get().getPassword().equals(hashPassword(password))) {
            return user;
        } else {
            return Optional.empty();
        }
    }

    // 添加 validateUser 方法
    public Optional<User> validateUser(String userName, String password) {
        // 从数据库中查找用户
        Optional<User> user = userRepository.findByUserName(userName);

        // 检查用户是否存在并验证密码
        if (user.isPresent() && user.get().getPassword().equals(hashPassword(password))) {
            return user;
        } else {
            return Optional.empty();
        }
    }

    // 密碼加鹽並雜湊（假设您有相应的加密逻辑）
    private String hashPassword(String password) {
        // 在這裡實現您的加鹽和雜湊邏輯
        return password;  // 这里可以替换为真正的密码加盐和散列算法
    }

    // 之前的注册方法
    public void register(User user) throws Exception {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new Exception("此手機號碼已被註冊");
        }
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
    }
}

