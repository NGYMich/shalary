package com.ngymich.shalary.domain.location;

import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.country.CountryWithStates;
import com.ngymich.shalary.domain.state.State;
import com.ngymich.shalary.infrastructure.backends.countriesnow.CountriesNowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Map<Country, List<String>> getCountriesWithTheirStates() {
        List<Country> countriesWithFlags = this.getCountriesWithFlags();
        List<CountryWithStates> countriesWithStates = this.countriesNowClient.getCountriesWithStates().getData();

        countriesWithStates.removeIf(countryWithState -> countryWithState.getName().equals("Congo"));
        Map<Country, List<String>> stringListMap = countriesWithStates
                .stream().collect(
                        Collectors.toMap(
                                countryWithState -> countriesWithFlags
                                        .stream()
                                        .filter(countryWithFlag -> countryWithFlag.getName().equals(countryWithState.getName()))
                                        .findFirst()
                                        .orElse(Country.builder().name(countryWithState.getName()).flag("").build()),
                                countryWithState -> countryWithState
                                        .getStates()
                                        .stream()
                                        .map(State::getName)
                                        .collect(Collectors.toList()))
                );

        return stringListMap;
//        Map<String, List<City>> collect = cities.stream().collect(groupingBy(City::getCountry, toSet()));


//        return collect;
    }

}
