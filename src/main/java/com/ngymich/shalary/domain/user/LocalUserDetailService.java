package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.authentication.LocalUser;
import com.ngymich.shalary.application.exceptions.ResourceNotFoundException;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Chinna
 */
@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        PersistableUser user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("PersistableUser " + email + " was not found in the database");
        }
        return createLocalUser(user);
    }

    @Transactional
    public LocalUser loadUserById(Long id) {
        PersistableUser user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("PersistableUser", "id", id));
        return createLocalUser(user);
    }

    private LocalUser createLocalUser(PersistableUser user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new LocalUser(user.getEmail(), user.getPassword(), true, true, true, true, authorities, user);
    }
}
