package com.ngymich.shalary.domain.forex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngymich.shalary.infrastructure.backends.freeForexApi.FreeForexApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ForexService {

    private final FreeForexApiClient freeForexApiClient;
    private Map<String, Double> forexTopPairs;

    @Autowired
    public ForexService(FreeForexApiClient freeForexApiClient) {
        this.freeForexApiClient = freeForexApiClient;
        refreshForex();
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void refreshForex() {
        try {
            log.info("Refreshing forex..");
            this.forexTopPairs = this.getForexForTopPairs();
        } catch (Exception e) {
            log.error("Error while refreshing forex : ", e);
        }
    }

    @Cacheable("forex")
    public Double getForexForPair(String pair) {
        try {
            log.info("Calling Forex Api to get pair {}", pair);
            String liveRate = this.freeForexApiClient.getLiveRateForPair(pair, "ultra", "c8a3cf4af735cb168a92");
            @SuppressWarnings("rawtypes") HashMap liveRates = new ObjectMapper().readValue(liveRate, HashMap.class);
            return (Double) liveRates.get(pair);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Error on Forex Call");
        }
        return 0D;
    }

    @Cacheable("forex")
    public Map<String, Double> getForexForTopPairs() {
        List<String> defaultCurrencies = Stream.of("EUR", "USD", "GBP", "JPY", "CHF", "AUD", "CAD").collect(Collectors.toList());
        List<String> forexPairs = new ArrayList<>();

        for (int i = 0; i < defaultCurrencies.size(); i++) {
            for (String defaultCurrency : defaultCurrencies) {
                if (!defaultCurrencies.get(i).equals(defaultCurrency))
                    forexPairs.add(defaultCurrencies.get(i) + "_" + defaultCurrency);
            }
        }
        return forexPairs.stream()
                .distinct()
                .collect(Collectors.toMap(name -> name, this::getForexForPair));
    }

    public Map<String, Double> getForexTopPairs() {
        log.info("Retrieving forexes...");
        return forexTopPairs;
    }
}
