package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.application.authentication.LocalUser;
import com.ngymich.shalary.application.user.UserDTO;
import com.ngymich.shalary.application.util.GeneralUtils;
import com.ngymich.shalary.config.security.user.CurrentUser;
import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.user.RequestUserDTO;
import com.ngymich.shalary.domain.user.User;
import com.ngymich.shalary.domain.user.UserServiceImpl;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService, LocationController locationController) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        log.info("Retrieving all users..");
        List<User> users = this.userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/mostPopularCountries")
    public ResponseEntity<?> getMostPopularCountriesFromUsers() {
        log.info("Retrieving most popular countries");
        List<Country> users = this.userService.getMostPopularCountriesFromUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable("user_id") Long userId) {
        log.info("Retrieving user with id " + userId);
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

//    @PostMapping("/retrieveUserWithPassword")
//    public ResponseEntity<?> getUserByPassword(@RequestBody RequestUserDTO requestUserDTO) {
//        log.info("Retrieving user {} with password. UserId : ", requestUserDTO.getId());
//        return ResponseEntity.ok(this.userService.verifyByPassword(requestUserDTO.getId(), requestUserDTO.getPassword()));
//    }

    @PostMapping("/retrieveUserWithPassword")
    public ResponseEntity<?> getUserByPassword(@RequestBody RequestUserDTO requestUserDTO) {
        log.info("Retrieving user {} with password.", requestUserDTO.getUsername());
        return ResponseEntity.ok(this.userService.getUserThroughPassword(requestUserDTO.getUsername(), requestUserDTO.getPassword()));
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDto) {
        try {
            log.info("Adding user " + userDto.getUsername());
            return ResponseEntity.ok(this.userService.addUser(userDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto) {
        try {
            log.info("Patching user " + userDto.getUsername());
            return ResponseEntity.ok(this.userService.updateUser(userDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/users")
    public List<PersistableUser> addUsers(@RequestBody List<UserDTO> userDTOS) {
        String usernames = userDTOS.stream().map(UserDTO::getUsername).collect(Collectors.joining(","));
        log.info("Adding users " + Arrays.toString(usernames.split(", ")));
        List<PersistableUser> addedUsers = new ArrayList<>();
        userDTOS.forEach(user -> {
            try {
                PersistableUser addedUser = this.userService.addUser(user);
                addedUsers.add(addedUser);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return addedUsers;
    }

    @Transactional
    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        this.userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/users/")
    public ResponseEntity<Long> deleteUsers(@RequestBody List<Integer> userIdsToDelete) {
        userIdsToDelete.forEach(userId -> {
            if (this.userService.getUserById(Long.valueOf(userId)).isPresent()) {
                this.userService.deleteUserById(Long.valueOf(userId));
            }
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/deleteUsersWithRange/{fromUserId}/{toUserId}")
    public ResponseEntity<Long> deleteUsersWithRange(@PathVariable int fromUserId, @PathVariable int toUserId) {
        for (int i = fromUserId; i <= toUserId; i++) {
            if (this.userService.getUserById((long) i).isPresent()) {
                this.userService.deleteUserById((long) i);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @GetMapping("/mod")
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }


//    @Transactional
//    @DeleteMapping(path = "/users")
//    public ResponseEntity<Long> deleteAllUsers() {
//        this.userService.deleteAll();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
