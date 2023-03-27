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
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("Algeria").build(), count, userDTOS.size());
        for (int i=0; i<2; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("Mongolia").build(), count, userDTOS.size());
        for (int i=0; i<3; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("America").build(), count, userDTOS.size());
        for (int i=0; i<4; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("France").build(), count, userDTOS.size());
        for (int i=0; i<5; i++) {
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("Portugal").build(), count, userDTOS.size());
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("Japan").build(), count, userDTOS.size());
        }
        for (int i=0; i<10; i++)
            this.userService.addUser(UserDTO.builder().username("").password("").email("").location("Nigeria").build(), count, userDTOS.size());

        List<Country> mostPopularCountries = userService.getMostPopularCountriesFromUsers();

        assertFalse(mostPopularCountries.isEmpty());
    }
}
