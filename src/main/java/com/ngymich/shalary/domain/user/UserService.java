package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.User.UserDTO;
import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.location.LocationService;
import com.ngymich.shalary.infrastructure.persistence.company.PersistableCompany;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryHistoryJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import com.ngymich.shalary.infrastructure.persistence.user.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserJpaRepository userRepository;
    private final SalaryHistoryJpaRepository salaryHistoryRepository;
    private final LocationService locationService;

    public List<Country> getMostPopularCountriesFromUsers() {

        List<Country> countriesWithFlags = locationService.getCountriesWithFlags();

        return this.getUsers()
                .stream()
                .collect(Collectors.groupingBy(User::getLocation, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(7)
                .collect(Collectors.toList()).stream().map(Map.Entry::getKey).collect(Collectors.toList())
                .stream()
                .map(countryName -> {
                    List<Country> collect = countriesWithFlags
                            .stream()
                            .filter(countryWithFlag -> countryWithFlag.getName().equals(countryName))
                            .collect(Collectors.toList());

                    String flag = null;
                    if (!collect.isEmpty()) {
                        flag = collect.get(0).getFlag();
                    }

                    return Country
                            .builder()
                            .name(countryName)
                            .flag(flag)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public UserService(UserJpaRepository userRepository, SalaryHistoryJpaRepository salaryHistoryRepository, LocationService locationService) {
        this.userRepository = userRepository;
        this.salaryHistoryRepository = salaryHistoryRepository;
        this.locationService = locationService;
    }

    public PersistableUser addUser(UserDTO userDto) {
        PersistableUser user = buildUser(userDto);
        this.userRepository.save(user);
        log.info("User {} saved", user.getUsername());
        return user;
    }

    public PersistableUser updateUser(UserDTO userDto) {
        // Retrieving user
        PersistableUser user = buildUser(userDto);
        PersistableUser userFromRepository = this.userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("user" + user.getUsername() + " not found"));

        // Retrieving salary history
        PersistableSalaryHistory salaryHistory = salaryHistoryRepository.getById(userFromRepository.getSalaryHistory().getId());

        // Setting salary infos in salary history with new user salary infos
        salaryHistory.setSalaryInfos(user.getSalaryHistory().getSalaryInfos());

        // Setting salary history for each salary info
        salaryHistory.getSalaryInfos().forEach(persistableSalaryInfo -> persistableSalaryInfo.setSalaryHistory(salaryHistory));

        // Setting salary history for the user
        userFromRepository.setSalaryHistory(salaryHistory);

        // Saving the entity
        userRepository.save(userFromRepository);

        log.info("User {} updated", user.getUsername());
        return userFromRepository;
    }

    private PersistableUser buildUser(UserDTO userDto) {
        if (userDto.getSalaryHistory() != null) {
            if (!userDto.getSalaryHistory().getSalaryInfos().isEmpty()) {
                userDto.getSalaryHistory().getSalaryInfos().forEach(salaryInfo -> salaryInfo.setSalaryHistory(userDto.getSalaryHistory()));
                userDto.getSalaryHistory()
                        .getSalaryInfos()
                        .forEach(salaryInfo -> {
                            Double totalSalary = 0D;
                            if (salaryInfo.getBaseSalary() == null) salaryInfo.setBaseSalary(0D);
                            if (salaryInfo.getBonusSalary() == null) salaryInfo.setBonusSalary(0D);
                            if (salaryInfo.getStockSalary() == null) salaryInfo.setStockSalary(0D);
                            totalSalary += salaryInfo.getBaseSalary();
                            totalSalary += salaryInfo.getBonusSalary();
                            totalSalary += salaryInfo.getStockSalary();
                            salaryInfo.setTotalSalary(totalSalary);
                            if (salaryInfo.getCompany() == null) {
                                salaryInfo.setCompany(PersistableCompany.builder().build());
                            } else {
//                                salaryInfo.getCompany().setSalaryInfo(salaryInfo);
                                salaryInfo.setCompany(salaryInfo.getCompany());
                            }
                        });
                sortSalaryHistoryByYearsOfExperience(userDto);
            }
            userDto.getSalaryHistory().setTotalYearsOfExperience();
        }

//        if (!isValidEmailAddress(userDto.getMail())) {
//            throw new Exception("Mail isn't valid.");
//        }

        return PersistableUser.builder()
                .id(userDto.getId())
                .username(Optional.of(userDto.getUsername().trim()).orElse(null))
                .password(userDto.getPassword())
                .mail(userDto.getMail().trim())
                .validated(userDto.isValidated())
                .mainSector(StringUtils.stripAccents(Optional.ofNullable((userDto.getMainSector())).map(String::trim).orElse(null)))
                .location(StringUtils.stripAccents(Optional.ofNullable((userDto.getLocation())).map(String::trim).orElse(null)))
                .education(StringUtils.stripAccents(Optional.ofNullable((userDto.getEducation())).map(String::trim).orElse(null)))
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .comment(StringUtils.stripAccents(Optional.ofNullable((userDto.getComment())).map(String::trim).orElse(null)))
                .salaryHistory(userDto.getSalaryHistory())
                .build();
    }

    private void sortSalaryHistoryByYearsOfExperience(UserDTO userDto) {
        userDto.getSalaryHistory().getSalaryInfos().sort(Comparator.comparing(PersistableSalaryInfo::getYearsOfExperience));
    }

    public List<User> getUsers() {
        List<Country> countriesWithFlags = this.locationService.getCountriesWithFlags();
        List<PersistableUser> persistableUsers = this.userRepository.findAll();
        log.info("Retrieved {} users", persistableUsers.size());
        return persistableUsers
                .stream()
                .map(persistableUser -> {
                    List<Country> filteredCountries = countriesWithFlags
                            .stream()
                            .filter(country -> country.getName().equals(persistableUser.getLocation()))
                            .collect(Collectors.toList());
                    String locationImage = null;
                    if (!filteredCountries.isEmpty()) locationImage = filteredCountries.get(0).getFlag();
                    return User
                            .builder()
                            .age(persistableUser.getAge())
                            .id(persistableUser.getId())
                            .comment(persistableUser.getComment())
                            .username(persistableUser.getUsername())
                            .education(persistableUser.getEducation())
                            .gender(persistableUser.getGender())
                            .mail(persistableUser.getMail())
                            .mainSector(persistableUser.getMainSector())
                            .password(persistableUser.getPassword())
                            .validated(persistableUser.isValidated())
                            .salaryHistory(persistableUser.getSalaryHistory())
                            .location(persistableUser.getLocation())
                            .locationImage(locationImage)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public Optional<PersistableUser> getUserById(Long userId) {
        return this.userRepository.findById(userId);
    }

    public Optional<PersistableUser> verifyByPassword(Long userId, String password) {
        Optional<PersistableUser> user = this.userRepository.findById(userId);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public void deleteUserById(Long userId) {
        PersistableUser user = this.userRepository.getById(userId);
        this.salaryHistoryRepository.deleteById(userId);
        this.userRepository.deleteById(userId);
        log.info("Deleted user [{}], id : {}", user.getUsername(), user.getId());

    }

    public void deleteAll() {
        this.userRepository.deleteAll();
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
