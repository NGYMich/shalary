package com.ngymich.shalary.domain.user;

import com.ngymich.shalary.application.user.UserDTO;
import com.ngymich.shalary.domain.country.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void getMostPopularCountries() throws Exception {

        for (int i=0; i<1; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").mail("").location("Algeria").build());
        for (int i=0; i<2; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").mail("").location("Mongolia").build());
        for (int i=0; i<3; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").mail("").location("America").build());
        for (int i=0; i<4; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").mail("").location("France").build());
        for (int i=0; i<5; i++) {
            this.userService.addUser(UserDTO.builder().username("").password("").mail("").location("Portugal").build());
            this.userService.addUser(UserDTO.builder().username("").password("").mail("").location("Japan").build());
        }
        for (int i=0; i<10; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").mail("").location("Nigeria").build());

        List<Country> mostPopularCountries = userService.getMostPopularCountriesFromUsers();

        assertFalse(mostPopularCountries.isEmpty());
    }
}
