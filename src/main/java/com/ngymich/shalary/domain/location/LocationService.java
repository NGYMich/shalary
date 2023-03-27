package com.ngymich.shalary.domain.location;

import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.country.CountryWithStates;
import com.ngymich.shalary.domain.state.State;
import com.ngymich.shalary.infrastructure.backends.countriesnow.CountriesNowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationService {

    private final CountriesNowClient countriesNowClient;
    private List<Country> countries;

    @Autowired
    public LocationService(CountriesNowClient countriesNowClient) {
        this.countriesNowClient = countriesNowClient;
        this.refreshCountries();
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void refreshCountries() {
        try {
            log.info("Refreshing countries..");
            this.countries = this.getCountriesWithFlags();
        } catch (Exception e) {
            log.error("Error while refreshing countries : ", e);
        }
    }

    @Cacheable("countries")
    public List<Country> getCountriesWithFlags() {
        return this.countriesNowClient.getCountriesWithFlags().getData();
    }


    @Cacheable("countries")
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

    public List<Country> getCountries() {
//        log.info("Retrieving countries...");
        return countries;
    }

}
