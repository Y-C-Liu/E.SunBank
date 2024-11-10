// UserController.java
package com.example.esunbank.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.esunbank.model.User;
import com.example.esunbank.service.UserService;
import com.example.esunbank.request.LoginRequest;  // 引入LoginRequest类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            // 打印用户数据以检查数据是否完整
            System.out.println("Registering user: " + user);

            // 验证每个字段是否为空
            if (user.getUserName() == null || user.getUserName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名不能为空");
            }
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("电子邮件不能为空");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码不能为空");
            }
            if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("手机号码不能为空");
            }

            // 调用服务注册用户
            userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功！");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("註冊失敗：" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> user = userService.validateUserByPhoneNumber(loginRequest.getPhoneNumber(), loginRequest.getPassword());
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
