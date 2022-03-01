package com.ngymich.shalary.infrastructure.backends.countriesnow;

import com.ngymich.shalary.domain.country.Country;
import lombok.Data;

import java.util.List;

@Data
public class CountriesNowCountriesWithFlagResponse {
    private String error;
    private String msg;
    private List<Country> data;
}
