package com.ngymich.shalary.infrastructure.backends.countriesnow;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountriesNowClientConfig {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
