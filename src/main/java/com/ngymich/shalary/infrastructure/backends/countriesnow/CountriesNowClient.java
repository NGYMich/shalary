package com.ngymich.shalary.infrastructure.backends.countriesnow;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "countriesNowClient", url = "https://countriesnow.space/api/v0.1/")
public interface CountriesNowClient {
    @GetMapping(value = "/countries/population/cities")
    CountriesNowCitiesResponse getCities();

    @GetMapping(value = "/countries/flag/images")
    CountriesNowCountriesWithFlagResponse getCountriesWithFlags();

    @GetMapping(value = "/countries/states")
    CountriesNowCountriesWithStatesResponse getCountriesWithStates();
}
