package com.ngymich.shalary.infrastructure.backends.countriesnow;

import com.ngymich.shalary.domain.city.City;
import com.ngymich.shalary.domain.country.CountryWithStates;
import lombok.Data;

import java.util.List;

@Data
public class CountriesNowCountriesWithStatesResponse {
    private String error;
    private String msg;
    private List<CountryWithStates> data;
}

