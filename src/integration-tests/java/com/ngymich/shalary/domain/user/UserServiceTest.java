package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.user.UserDTO;
import com.ngymich.shalary.domain.country.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void getMostPopularCountries() throws Exception {

        for (int i = 0; i < 1; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("Algeria").build(), new AtomicInteger(1), 1);

        List<Country> mostPopularCountries = userService.getMostPopularCountriesFromUsers();

        assertFalse(mostPopularCountries.isEmpty());
    }
}
