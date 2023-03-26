package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.authentication.LocalUser;
import com.ngymich.shalary.application.authentication.SignUpRequest;
import com.ngymich.shalary.application.exceptions.UserAlreadyExistAuthenticationException;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    public PersistableUser registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    PersistableUser findUserByEmail(String email);

    Optional<PersistableUser> findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
