package com.ngymich.shalary.config.security.user;

import com.ngymich.shalary.application.authentication.SocialProvider;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import com.ngymich.shalary.infrastructure.persistence.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserJpaRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    PersistableUser createUserIfNotFound(final String email) {
        PersistableUser user = userRepository.findByEmail(email);
        if (user == null) {
            user = new PersistableUser();
            user.setUsername("Admin");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("admin@"));
            user.setProvider(SocialProvider.LOCAL.getProviderType());
            LocalDate now = LocalDate.now();
            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
