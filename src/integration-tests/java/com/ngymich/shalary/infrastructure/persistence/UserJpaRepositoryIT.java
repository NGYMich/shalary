package com.ngymich.shalary.infrastructure.persistence;

import com.ngymich.shalary.infrastructure.persistence.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserJpaRepositoryIT {

    @Autowired private UserJpaRepository userJpaRepository;

    @Test
    void should_test() {
        this.userJpaRepository.findAll();
    }

}
