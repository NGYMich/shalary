package com.ngymich.shalary.infrastructure.backends.freeForexApi;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class FreeForexApiClientConfig {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
