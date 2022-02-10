package com.ngymich.shalary.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    @GetMapping("/salaries")
    public ResponseEntity<?> getSalaries() {
        log.info("Retrieving salaries");
        return null;
    }
}
