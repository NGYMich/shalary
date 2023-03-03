package com.ngymich.shalary.web.controller;

import com.ngymich.shalary.domain.forex.ForexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class ForexController {
    private final ForexService forexService;

    @Autowired
    public ForexController(ForexService forexService) {
        this.forexService = forexService;
    }

    @GetMapping("/forex/topForexes")
    public ResponseEntity<?> getTopForexes() {
        log.info("Retrieving forexes...");
        Map<String, Double> forexes = null;
        try {
            forexes = this.forexService.getForexForTopPairs();
            log.info("Forexes retrieved : {}", forexes);
            return ResponseEntity.ok(forexes);
        } catch (Exception e) {
            log.error("Failed to retrieve forexes (probably due to limit)" + e);
        }
        return ResponseEntity.ok(forexes);
    }

}
