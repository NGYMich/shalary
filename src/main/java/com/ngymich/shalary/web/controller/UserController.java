package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.application.authentication.LocalUser;
import com.ngymich.shalary.application.user.UserDTO;
import com.ngymich.shalary.application.util.GeneralUtils;
import com.ngymich.shalary.config.security.user.CurrentUser;
import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.user.UserService;
import com.ngymich.shalary.domain.user.UserServiceImpl;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService, LocationController locationController) {
        this.userService = userService;
    }

    // ------------------------------------------- GET USERS
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        log.info("Retrieving all users..");
        List<UserDTO> users = this.userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/usersWithSalaryHistory")
    public ResponseEntity<?> getUsersWithSalaryHistory() {
        log.info("Retrieving all users with salary history..");
        List<UserDTO> users = this.userService.getUsersWithSalaryHistory();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable("user_id") Long userId) {
        log.info("Retrieving user with id " + userId);
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }


    @GetMapping(path = "/getUsersFromPageAndPageSize/{page}/{pageSize}")
    public ResponseEntity<?> getUsersFromTo(@PathVariable int page, @PathVariable int pageSize) {
        List<UserDTO> users;
        try {
            log.info("Retrieving users from id {} to {} ({} users at page {}) ..", page * pageSize + 1, page * (pageSize + 1), pageSize, page);
            users = this.userService.getUsersFromPageAndPageSize(page, pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/getUsersFromCountry/{countryName}")
    public ResponseEntity<?> getUsersFromTo(@PathVariable String countryName) {
        List<UserDTO> users = null;
        try {
            log.info("Retrieving users from {}", countryName);
            users = this.userService.getUsersFromCountry(countryName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(users);
    }


    // ------------------------------------------- ADD USERS

    @PostMapping("/users")
    public List<PersistableUser> addUsers(@RequestBody List<UserDTO> userDTOS) {
        String usernames = userDTOS.stream().map(UserDTO::getUsername).collect(Collectors.joining(","));
        log.info("Adding users " + Arrays.toString(usernames.split(", ")));
        List<PersistableUser> addedUsers = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        userDTOS.forEach(user -> {
            try {
                count.getAndIncrement();
                PersistableUser addedUser = this.userService.addUser(user, count, userDTOS.size());
                addedUsers.add(addedUser);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return addedUsers;
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDto) {
        try {
            log.info("Adding user " + userDto.getUsername());
            return ResponseEntity.ok(this.userService.addUser(userDto, new AtomicInteger(1), 1));
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


    // ------------------------------------------- DELETE  USERS
    @Transactional
    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        this.userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/users/")
    public ResponseEntity<Long> deleteUsers(@RequestBody List<Integer> userIdsToDelete) {
        this.userService.deleteUsers(userIdsToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/deleteUsersWithRange/{fromUserId}/{toUserId}")
    public ResponseEntity<Long> deleteUsersWithRange(@PathVariable int fromUserId, @PathVariable int toUserId) {
        this.userService.deleteUsersWithRange(fromUserId, toUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ------------------------------------------- OTHERS
    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }


    @GetMapping("/mostPopularCountries")
    public ResponseEntity<?> getMostPopularCountriesFromUsers() {
        log.info("Retrieving most popular countries");
        List<Country> users = this.userService.getMostPopularCountriesFromUsers();
        return ResponseEntity.ok(users);
    }

//    @GetMapping("/all")
//    public ResponseEntity<?> getContent() {
//        return ResponseEntity.ok("Public content goes here");
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity<?> getUserContent() {
//        return ResponseEntity.ok("User content goes here");
//    }
//
//    @GetMapping("/admin")
//    public ResponseEntity<?> getAdminContent() {
//        return ResponseEntity.ok("Admin content goes here");
//    }
//
//    @GetMapping("/mod")
//    public ResponseEntity<?> getModeratorContent() {
//        return ResponseEntity.ok("Moderator content goes here");
//    }


//    @Transactional
//    @DeleteMapping(path = "/users")
//    public ResponseEntity<Long> deleteAllUsers() {
//        this.userService.deleteAll();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
