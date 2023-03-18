package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.application.User.UserDTO;
import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.user.RequestUserDTO;
import com.ngymich.shalary.domain.user.User;
import com.ngymich.shalary.domain.user.UserService;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, LocationController locationController) {
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


//    @Transactional
//    @DeleteMapping(path = "/users")
//    public ResponseEntity<Long> deleteAllUsers() {
//        this.userService.deleteAll();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
