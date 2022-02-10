package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.User.UserDTO;
import com.ngymich.shalary.infrastructure.persistence.user.IUserRepository;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PersistableUser addUser(UserDTO userDto) {
        PersistableUser user = PersistableUser.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .mail(userDto.getMail())
                .mainSector(userDto.getMainSector())
                .location(userDto.getLocation())
                .education(userDto.getEducation())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .salaryHistory(userDto.getSalaryHistory())
                .build();

        this.userRepository.save(user);
        return user;
    }

    public List<PersistableUser> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<PersistableUser> getUserById(Long userId) {
        return this.userRepository.findById(userId);
    }

}
