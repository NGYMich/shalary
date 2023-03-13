package com.ngymich.shalary.infrastructure.backends.freeForexApi;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "freeForexApi", url = "https://free.currconv.com/api/v7")
public interface FreeForexApiClient {

    @Cacheable("forex")
    @GetMapping(value = "/convert")
    String getLiveRateForPair(@RequestParam String q, @RequestParam String compact, @RequestParam String apiKey);
}
