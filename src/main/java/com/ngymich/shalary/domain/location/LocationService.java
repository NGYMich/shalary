package com.ngymich.shalary.domain.location;

import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.user.User;
import com.ngymich.shalary.domain.user.UserService;
import com.ngymich.shalary.infrastructure.backends.countriesnow.CountriesNowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final CountriesNowClient countriesNowClient;

    @Autowired
    public LocationService(CountriesNowClient countriesNowClient) {
        this.countriesNowClient = countriesNowClient;
    }

    public List<Country> getCountriesWithFlags() {
        return this.countriesNowClient.getCountriesWithFlags().getData();
    }


}
