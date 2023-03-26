package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.authentication.LocalUser;
import com.ngymich.shalary.application.authentication.SignUpRequest;
import com.ngymich.shalary.application.authentication.SocialProvider;
import com.ngymich.shalary.application.exceptions.OAuth2AuthenticationProcessingException;
import com.ngymich.shalary.application.exceptions.UserAlreadyExistAuthenticationException;
import com.ngymich.shalary.application.user.UserDTO;
import com.ngymich.shalary.application.util.GeneralUtils;
import com.ngymich.shalary.config.security.user.OAuth2UserInfo;
import com.ngymich.shalary.config.security.user.OAuth2UserInfoFactory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserJpaRepository userRepository;
    private final SalaryHistoryJpaRepository salaryHistoryRepository;
    private final LocationService locationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // move this to location controller
    public List<Country> getMostPopularCountriesFromUsers() {

        List<Country> countriesWithFlags = locationService.getCountries();

        return this.getUsersWithSalaryHistory()
                .stream()
                .collect(Collectors.groupingBy(UserDTO::getLocation, Collectors.counting()))
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

    public UserServiceImpl(UserJpaRepository userRepository, SalaryHistoryJpaRepository salaryHistoryRepository, LocationService locationService) {
        this.userRepository = userRepository;
        this.salaryHistoryRepository = salaryHistoryRepository;
        this.locationService = locationService;
    }

    public PersistableUser addUser(UserDTO userDto) {
        PersistableUser user = buildUser(userDto);
        PersistableUser savedUser = this.userRepository.save(user);
        log.info("User {} saved", user.getUsername());
        return savedUser;
    }

    public PersistableUser updateUser(UserDTO userDto) {
        // Retrieving user
        PersistableUser user = buildUser(userDto);
        PersistableUser userFromRepository = this.userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("user" + user.getUsername() + " not found"));

        PersistableSalaryHistory salaryHistory;
        // Retrieving salary history
        salaryHistory = userFromRepository.getSalaryHistory() != null ? salaryHistoryRepository.getById(userFromRepository.getSalaryHistory().getId()) : new PersistableSalaryHistory();

        // Setting salary infos in salary history with new user salary infos
        setSalaryHistoryInformations(user.getSalaryHistory(), salaryHistory);

        // Setting salary history for each salary info
        setSalaryHistoryForEachSalaryInfo(salaryHistory);

        // Setting salary history for the user
        setUserInformations(userDto, userFromRepository, salaryHistory);

        // Saving the entity
        userRepository.save(userFromRepository);

        log.info("User {} updated", user.getUsername());
        return userFromRepository;
    }

    private void setUserInformations(UserDTO userDto, PersistableUser userFromRepository, PersistableSalaryHistory salaryHistory) {
        userFromRepository.setEmail(userDto.getEmail());

        if (userDto.getPassword() != null && !userDto.getPassword().equals("")) {
            userFromRepository.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userFromRepository.setUsername(userDto.getUsername());
        userFromRepository.setMainSector(userDto.getMainSector());
        userFromRepository.setLocation(userDto.getLocation());
        userFromRepository.setCity(userDto.getCity());
        userFromRepository.setEducation(userDto.getEducation());
        userFromRepository.setAge(userDto.getAge());
        userFromRepository.setGender(userDto.getGender());
        userFromRepository.setComment(userDto.getComment());
        userFromRepository.setSalaryHistory(salaryHistory);
        userFromRepository.setModifiedDate(LocalDate.now());
        userFromRepository.setThumbsUp(userDto.getThumbsUp());
        userFromRepository.setThumbsDown(userDto.getThumbsDown());
        userFromRepository.setValidated(userDto.isValidated());

    }

    private static void setSalaryHistoryInformations(PersistableSalaryHistory persistableSalaryHistory, PersistableSalaryHistory salaryHistory) {
        salaryHistory.setSalaryInfos(persistableSalaryHistory.getSalaryInfos());
        salaryHistory.setTotalYearsOfExperience(persistableSalaryHistory.getTotalYearsOfExperience());
        salaryHistory.setSalaryCurrency(persistableSalaryHistory.getSalaryCurrency());
    }

    private PersistableUser buildUser(UserDTO userDto) {
        return PersistableUser.builder()
                .id(userDto.getId())
                .email(userDto.getEmail().trim())
                .password(userDto.getPassword())
                .username(Optional.of(userDto.getUsername().trim()).orElse(null))
                .mainSector(StringUtils.stripAccents(Optional.ofNullable((userDto.getMainSector())).map(String::trim).orElse(null)))
                .location(StringUtils.stripAccents(Optional.ofNullable((userDto.getLocation())).map(String::trim).orElse(null)))
                .education(StringUtils.stripAccents(Optional.ofNullable((userDto.getEducation())).map(String::trim).orElse(null)))
                .city((userDto.getCity() != null && userDto.getCity().equals("")) ? null : userDto.getCity())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .comment(StringUtils.stripAccents(Optional.ofNullable((userDto.getComment())).map(String::trim).orElse(null)))
                .salaryHistory(buildSalaryHistory(userDto))
                .modifiedDate(LocalDate.now())
                .provider(userDto.getProvider())
                .thumbsDown(userDto.getThumbsDown())
                .thumbsUp(userDto.getThumbsUp())
                .validated(userDto.isValidated())
                .build();
    }

    private PersistableSalaryHistory buildSalaryHistory(UserDTO userDto) {
        PersistableSalaryHistory salaryHistory = userDto.getSalaryHistory();
        if (salaryHistory != null) {
            if (!salaryHistory.getSalaryInfos().isEmpty()) {
                setTotalYearsOfExperience(salaryHistory);
                setSalaryHistoryForEachSalaryInfo(salaryHistory);
                buildSalaryInfos(salaryHistory);
                sortSalaryHistoryByYearsOfExperience(userDto);
            }
        }
        return salaryHistory;
    }

    private static void buildSalaryInfos(PersistableSalaryHistory salaryHistory) {
        salaryHistory
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
                    salaryInfo.getCompany().setSalaryInfo(salaryInfo);
                    if (salaryInfo.getCompany() == null) {
                        salaryInfo.setCompany(PersistableCompany.builder().build());
                    } else {
                        salaryInfo.setCompany(salaryInfo.getCompany());
                    }
                });
    }

    private static void setSalaryHistoryForEachSalaryInfo(PersistableSalaryHistory salaryHistory) {
        salaryHistory.getSalaryInfos().forEach(salaryInfo -> salaryInfo.setSalaryHistory(salaryHistory));
    }

    private static void setTotalYearsOfExperience(PersistableSalaryHistory salaryHistory) {
        Float latestYearsOfExperience = salaryHistory.getSalaryInfos().get(salaryHistory.getSalaryInfos().size() - 1).getYearsOfExperience();
        if (salaryHistory.getTotalYearsOfExperience() < latestYearsOfExperience) {
            salaryHistory.setTotalYearsOfExperience(latestYearsOfExperience);
        }
    }

//    private Double calculateTotalSalary(PersistableSalaryInfo salaryInfo) {
//        double totalSalary = 0D;
//        if (salaryInfo.getBaseSalary() != null) totalSalary += salaryInfo.getBaseSalary();
//        if (salaryInfo.getBonusSalary() != null) totalSalary += salaryInfo.getBonusSalary();
//        if (salaryInfo.getStockSalary() != null) totalSalary += salaryInfo.getStockSalary();
//        return totalSalary;
//    }

    private void sortSalaryHistoryByYearsOfExperience(UserDTO userDto) {
        userDto.getSalaryHistory().getSalaryInfos().sort(Comparator.comparing(PersistableSalaryInfo::getYearsOfExperience));
    }

    public List<UserDTO> getUsers() {
        List<PersistableUser> persistableUsers = this.userRepository.findAll();
        log.info("Retrieved {} users", persistableUsers.size());
        return persistableUsers
                .stream()
                .sorted(Comparator.comparing(PersistableUser::getModifiedDate).reversed())
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDTO toUserDto(PersistableUser persistableUser) {
        List<Country> filteredCountries = this.locationService.getCountries()
                .stream()
                .filter(country -> country.getName().equals(persistableUser.getLocation()))
                .collect(Collectors.toList());
        String locationImage = null;
        if (!filteredCountries.isEmpty()) locationImage = filteredCountries.get(0).getFlag();
        return UserDTO
                .builder()
                .id(persistableUser.getId())
                .email(persistableUser.getEmail())
                .password(persistableUser.getPassword())
                .username(persistableUser.getUsername())
                .mainSector(persistableUser.getMainSector())
                .location(persistableUser.getLocation())
                .locationImage(locationImage)
                .city(persistableUser.getCity())
                .education(persistableUser.getEducation())
                .age(persistableUser.getAge())
                .gender(persistableUser.getGender())
                .comment(persistableUser.getComment())
                .salaryHistory(persistableUser.getSalaryHistory())
                .thumbsUp(persistableUser.getThumbsUp())
                .thumbsDown(persistableUser.getThumbsDown())
                .validated(persistableUser.isValidated())
                .createdDate(persistableUser.getCreatedDate())
                .modifiedDate(persistableUser.getModifiedDate())
                .build();
    }

    @Override
    public List<UserDTO> getUsersWithSalaryHistory() {
        return this.getUsers().stream()
                .filter(userDTO -> userDTO.getSalaryHistory() != null)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        try {
            return toUserDto(this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Could not find user " + userId)));
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    public Optional<PersistableUser> getUserThroughPassword(String username, String password) {
        Optional<PersistableUser> user = this.userRepository.findAll()
                .stream()
                .filter(persistableUser -> persistableUser.getUsername().equals(username) && persistableUser.getPassword().equals(password))
                .findFirst();

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public void deleteUserById(Long userId) {
        PersistableUser user = this.userRepository.getById(userId);
        this.userRepository.deleteById(userId);
        log.info("Deleted user [{}], id : {}", user.getUsername(), user.getId());
    }

    public void deleteUsersWithRange(int fromUserId, int toUserId) {
        for (int i = fromUserId; i <= toUserId; i++) {
            if (this.userRepository.findById(((long) i)).isPresent()) {
                this.deleteUserById((long) i);
            }
        }
    }

    public void deleteUsers(List<Integer> userIdsToDelete) {
        userIdsToDelete.forEach(userId -> {
            if (this.userRepository.findById(Long.valueOf(userId)).isPresent()) {
                this.deleteUserById(Long.valueOf(userId));
            }
        });
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

    @Override
    @Transactional(value = "transactionManager")
    public PersistableUser registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
        if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
            throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
        }
        PersistableUser user = buildUser(signUpRequest);
        LocalDate now = LocalDate.now();
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    private PersistableUser buildUser(final SignUpRequest formDTO) {
        PersistableUser user = new PersistableUser();
        user.setUsername(formDTO.getUsername());
        user.setEmail(formDTO.getEmail());
        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
        user.setProvider(formDTO.getSocialProvider().getProviderType());
        user.setCreatedDate(LocalDate.now());
        return user;
    }

    @Override
    public PersistableUser findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        } else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        PersistableUser user = findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
                throw new OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userDetails);
        }

        return LocalUser.create(user, attributes, idToken, userInfo);
    }

    private PersistableUser updateExistingUser(PersistableUser existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return SignUpRequest.builder()
                .providerUserId(oAuth2UserInfo.getId())
                .username(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .socialProvider(GeneralUtils.toSocialProvider(registrationId))
                .password("changeit")
                .build();
    }

    @Override
    public Optional<PersistableUser> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
