package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.User.UserDTO;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryHistoryJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryInfosJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.user.UserJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserJpaRepository userRepository;
    private final SalaryHistoryJpaRepository salaryHistoryRepository;
    private final SalaryInfosJpaRepository salaryInfosJpaRepository;


    public UserService(UserJpaRepository userRepository, SalaryHistoryJpaRepository salaryHistoryRepository, SalaryInfosJpaRepository salaryInfosJpaRepository) {
        this.userRepository = userRepository;
        this.salaryHistoryRepository = salaryHistoryRepository;
        this.salaryInfosJpaRepository = salaryInfosJpaRepository;
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

//        this.salaryInfosJpaRepository.saveAll(userDto.getSalaryHistory().getSalariesList());
        this.userRepository.save(user);
        return user;
    }

    public List<PersistableUser> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<PersistableUser> getUserById(Long userId) {
        return this.userRepository.findById(userId);
    }

    public void deleteUserById(Long userId) {
        this.salaryHistoryRepository.deleteById(userId);
        this.userRepository.deleteById(userId);
    }

}
