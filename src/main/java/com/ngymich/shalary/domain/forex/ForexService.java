package com.ngymich.shalary.domain.forex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngymich.shalary.infrastructure.backends.freeForexApi.FreeForexApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    public ForexService(FreeForexApiClient freeForexApiClient) {
        this.freeForexApiClient = freeForexApiClient;
    }

    @Cacheable("singleForexPair")
    public Double getForexForPair(String pair) {

        try {
            log.info("Calling Forex Api to get pairs...");
            String liveRate = this.freeForexApiClient.getLiveRateForPair(pair, "ultra", "46ffa9bab965104a4b74");
            @SuppressWarnings("rawtypes") HashMap mapping = new ObjectMapper().readValue(liveRate, HashMap.class);
            return (Double) mapping.get(pair);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Error on Forex Call");
        }
        return 0D;
    }

    @Cacheable("allForexPairs")
    public Map<String, Double> getForexForTopPairs() {
        List<String> defaultCurrencies = Stream.of("EUR", "USD", "GBP", "JPY", "CHF", "AUD", "CAD").collect(Collectors.toList());
//        List<String> defaultCurrencies = Stream.of("GBP", "USD").collect(Collectors.toList());
        List<String> forexPairs = new ArrayList<>();

        for (int i = 0; i < defaultCurrencies.size(); i++) {
            for (String defaultCurrency : defaultCurrencies) {
                if (!defaultCurrencies.get(i).equals(defaultCurrency))
                    forexPairs.add(defaultCurrencies.get(i) + "_" + defaultCurrency);
            }
        }
        Map<String, Double> result = forexPairs.stream()
                .distinct()
                .collect(Collectors.toMap(name -> name, this::getForexForPair));
        return result;
    }

}
