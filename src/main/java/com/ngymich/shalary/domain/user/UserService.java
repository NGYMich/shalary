package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.authentication.LocalUser;
import com.ngymich.shalary.application.authentication.SignUpRequest;
import com.ngymich.shalary.application.exceptions.UserAlreadyExistAuthenticationException;
import com.ngymich.shalary.application.user.UserDTO;
import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    PersistableUser registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    PersistableUser findUserByEmail(String email);

    Optional<PersistableUser> findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

    List<Country> getMostPopularCountriesFromUsers();

    List<UserDTO> getUsers();

    List<UserDTO> getUsersWithSalaryHistory();

    UserDTO toUserDto(PersistableUser persistableUser);

    PersistableUser addUser(UserDTO userDto);

    PersistableUser updateUser(UserDTO userDto);

    UserDTO getUserById(Long userId);

    Optional<PersistableUser> getUserThroughPassword(String username, String password);

    void deleteUserById(Long userId);

    void deleteAll();

    void deleteUsersWithRange(int fromUserId, int toUserId);

    void deleteUsers(List<Integer> userIdsToDelete);
}
