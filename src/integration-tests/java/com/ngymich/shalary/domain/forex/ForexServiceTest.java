package com.ngymich.shalary.domain.forex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ForexServiceTest {

    @Autowired
    private ForexService forexService;

    @Test
    void should_get_forex() {
        Double rate = this.forexService.getForexForPair("USD_EUR");
        assertNotNull(rate);
    }

    @Test
    void should_get_all_forex() {
        Map<String, Double> rates = this.forexService.getForexForTopPairs();



        assertNotNull(rates);
    }
}
