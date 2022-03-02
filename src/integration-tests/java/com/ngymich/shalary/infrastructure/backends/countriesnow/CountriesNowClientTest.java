package com.ngymich.shalary.infrastructure.backends.countriesnow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class CountriesNowClientTest {

    @Autowired
    private CountriesNowClient countriesNowClient;

    @Test
    void should_get_countries() {
        CountriesNowCitiesResponse cities = countriesNowClient.getCities();
        CountriesNowCountriesWithFlagResponse countries = countriesNowClient.getCountriesWithFlags();

        assertFalse(countries.getData().isEmpty());
    }

}
