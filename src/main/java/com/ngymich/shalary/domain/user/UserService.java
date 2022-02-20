package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.User.UserDTO;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryHistoryJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import com.ngymich.shalary.infrastructure.persistence.user.UserJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserJpaRepository userRepository;
    private final SalaryHistoryJpaRepository salaryHistoryRepository;


    public UserService(UserJpaRepository userRepository, SalaryHistoryJpaRepository salaryHistoryRepository) {
        this.userRepository = userRepository;
        this.salaryHistoryRepository = salaryHistoryRepository;
    }

    public PersistableUser addUser(UserDTO userDto) throws Exception {
        userDto.getSalaryHistory().getSalaryInfos().forEach(salaryInfo -> salaryInfo.setSalaryHistory(userDto.getSalaryHistory()));
        PersistableUser user = buildUser(userDto);

        this.userRepository.save(user);
        return user;
    }

    private PersistableUser buildUser(UserDTO userDto) throws Exception {

        userDto.getSalaryHistory()
                .getSalaryInfos()
                .forEach(salaryInfo -> {
                    Double totalSalary = 0D;
                    if (salaryInfo.getBaseSalary() != null) totalSalary += salaryInfo.getBaseSalary();
                    if (salaryInfo.getBonusSalary() != null) totalSalary += salaryInfo.getBonusSalary();
                    if (salaryInfo.getStockSalary() != null) totalSalary += salaryInfo.getStockSalary();
                    salaryInfo.setTotalSalary(totalSalary);
                });

//        if (!isValidEmailAddress(userDto.getMail())) {
//            throw new Exception("Mail isn't valid.");
//        }

        sortSalaryHistoryByYearsOfExperience(userDto);

        return PersistableUser.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .mail(userDto.getMail())
                .validated(userDto.isValidated())
                .mainSector(StringUtils.stripAccents(userDto.getMainSector()))
                .location(StringUtils.stripAccents(userDto.getLocation()))
                .education(StringUtils.stripAccents(userDto.getEducation()))
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .salaryHistory(userDto.getSalaryHistory())
                .build();
    }

    private void sortSalaryHistoryByYearsOfExperience(UserDTO userDto) {
        userDto.getSalaryHistory().getSalaryInfos().sort(Comparator.comparing(PersistableSalaryInfo::getYearsOfExperience));
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

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
