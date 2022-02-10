package com.ngymich.shalary.domain.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

/*    public User addUser(UserDTO userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .mail(userDto.getMail())
                .mainSector(userDto.getMainSector())
                .location(userDto.getLocation())
                .education(userDto.getEducation())
                .age(userDto.getAge())
                .gender(userDto.getGender())
//                .salaryHistory(userDto.getSalaryHistory())
                .build();

        this.userRepository.save(user);
        return user;
    }*/

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return this.userRepository.findById(userId);
    }

}
