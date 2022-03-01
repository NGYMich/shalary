package com.ngymich.shalary.infrastructure.backends.countriesnow;

import com.ngymich.shalary.domain.city.City;
import lombok.Data;

import java.util.List;

@Data
public class CountriesNowCitiesResponse {
    private String error;
    private String msg;
    private List<City> data;
}

