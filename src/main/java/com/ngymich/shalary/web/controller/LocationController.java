package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.domain.country.Country;
import com.ngymich.shalary.domain.location.LocationService;
import com.ngymich.shalary.infrastructure.backends.countriesnow.CountriesNowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public ResponseEntity<?> getCountriesWithFlags() {
        log.info("Retrieving countries with flags");
        List<Country> countries = this.locationService.getCountriesWithFlags();
        return ResponseEntity.ok(countries);
    }

}
