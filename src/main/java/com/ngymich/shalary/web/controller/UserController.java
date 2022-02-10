package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.application.User.UserDTO;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import com.ngymich.shalary.domain.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        log.info("Retrieving all users");
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable("user_id") Long userId) {
        log.info("Retrieving user " + userId);
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }


    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDto) {
        log.info("Adding user " + userDto.getUsername());
        userDto.getSalaryHistory().getSalaryInfos().forEach(salaryInfo -> salaryInfo.setSalaryHistory(userDto.getSalaryHistory()));
        return ResponseEntity.ok(this.userService.addUser(userDto));
    }

    @Transactional
    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        this.userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
