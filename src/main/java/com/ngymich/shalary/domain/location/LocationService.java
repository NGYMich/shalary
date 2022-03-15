package com.ngymich.shalary.domain.location;

import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.country.CountryWithStates;
import com.ngymich.shalary.domain.state.State;
import com.ngymich.shalary.infrastructure.backends.countriesnow.CountriesNowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Country> getCountriesWithTheirStates() {
        List<Country> countriesWithFlags = this.getCountriesWithFlags();
        List<CountryWithStates> countriesWithStates = this.countriesNowClient.getCountriesWithStates().getData();
        countriesWithStates.removeIf(countryWithState -> countryWithState.getName().equals("Congo"));
        return countriesWithStates.stream()
                .map(countryWithState -> Country.builder()
                        .name(countryWithState.getName())
                        .states(countryWithState.getStates().stream().map(State::getName).collect(Collectors.toList()))
                        .flag(countriesWithFlags.stream()
                                .filter(countryWithFlag -> countryWithFlag.getName().equals(countryWithState.getName()))
                                .findFirst()
                                .orElse(Country.builder().name(countryWithState.getName()).flag("").build())
                                .getFlag()
                        )
                        .build())
                .collect(Collectors.toList());
    }

}
