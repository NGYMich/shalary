package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.domain.user.User;
import com.ngymich.shalary.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        log.info("Retrieving users");
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable("user_id") Long userId) {
        log.info("Retrieving users");
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }


    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        log.info("Retrieving users");


        return null;
    }
}
