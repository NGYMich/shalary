package com.ngymich.shalary.domain.location;

import com.ngymich.shalary.domain.country.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class LocationServiceIT {

    @Autowired
    private LocationService locationService;

    @Test
    void getCountriesWithFlags() {

    }

    @Test
    void should_get_countries_with_their_states() {
        List<Country> countriesWithTheirStates = locationService.getCountriesWithTheirStates();
        assertFalse(countriesWithTheirStates.isEmpty());
    }
}
