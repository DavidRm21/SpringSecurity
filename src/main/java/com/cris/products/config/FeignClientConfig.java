package com.cris.products.config;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@Configuration
public class FeignClientConfig {

    @Bean
    @LoadBalanced
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
