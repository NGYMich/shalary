package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.User.UserDTO;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryHistoryJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryInfosJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import com.ngymich.shalary.infrastructure.persistence.user.UserJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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


        PersistableUser user = buildUser(userDto);
        this.userRepository.save(user);
        return user;
    }

    private PersistableUser buildUser(UserDTO userDto) {


        return PersistableUser.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .mail(userDto.getMail())
                .mainSector(StringUtils.stripAccents(userDto.getMainSector()))
                .location(StringUtils.stripAccents(userDto.getLocation()))
                .education(StringUtils.stripAccents(userDto.getEducation()))
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .salaryHistory(userDto.getSalaryHistory())
                .build();
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
