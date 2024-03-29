package com.ngymich.shalary.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean
    public CaffeineCache forexCache() {
        return new CaffeineCache("forex",
                Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build());
    }

    @Bean
    public CaffeineCache countriesCache() {
        return new CaffeineCache("countries",
                Caffeine.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).build());
    }

    @Bean
    public CaffeineCache mostPopularCountriesCache() {
        return new CaffeineCache("mostPopularCountries",
                Caffeine.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).build());
    }
}
