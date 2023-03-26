package com.ngymich.shalary.config.security.user;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.ngymich.shalary.application.authentication.SocialProvider;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import com.ngymich.shalary.infrastructure.persistence.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
            user.setDisplayName("Admin");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("admin@"));
            user.setProvider(SocialProvider.LOCAL.getProviderType());
            user.setEnabled(true);
            Date now = Calendar.getInstance().getTime();
//            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
