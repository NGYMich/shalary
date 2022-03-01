package com.ngymich.shalary.infrastructure.backends.countriesnow;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "countriesNowClient", url = "https://countriesnow.space/")
public interface CountriesNowClient {

    @GetMapping(value = "/api/v0.1/countries/population/cities")
    CountriesNowCitiesResponse getCities();

    @GetMapping(value = "/api/v0.1/countries/flag/images")
    CountriesNowCountriesWithFlagResponse getCountriesWithFlags();


}
