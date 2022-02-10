package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.domain.salary.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/salaryHistories")
    public ResponseEntity<?> getSalaryHistories() {
        log.info("Retrieving salary histories");
        return ResponseEntity.ok(this.salaryService.getSalaryHistories());
    }

    @GetMapping("/salaryInfos")
    public ResponseEntity<?> getSalaryInfos() {
        log.info("Retrieving salaries informations");
        return ResponseEntity.ok(this.salaryService.getSalaryInfos());
    }
}
