package com.ngymich.shalary.domain.location;

import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.infrastructure.backends.countriesnow.CountriesNowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private final CountriesNowClient countriesNowClient;

    public LocationService(CountriesNowClient countriesNowClient) {
        this.countriesNowClient = countriesNowClient;
    }

    public List<Country> getCountriesWithFlags() {
        return this.countriesNowClient.getCountriesWithFlags().getData();
    }

}
