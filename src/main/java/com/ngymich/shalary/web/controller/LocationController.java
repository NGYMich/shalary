package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.infrastructure.backends.countriesnow.CountriesNowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationController {
    private final CountriesNowClient countriesNowClient;

    @Autowired
    public LocationController(CountriesNowClient countriesNowClient) {
        this.countriesNowClient = countriesNowClient;
    }

    @GetMapping("/locations")
    public ResponseEntity<?> getCountriesWithFlags() {
        log.info("Retrieving countries with flags");
        return ResponseEntity.ok(this.countriesNowClient.getCountriesWithFlags().getData());
    }

}
