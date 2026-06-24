package com.flowpay.flowpaybackend.config;

import com.flowpay.flowpaybackend.service.DistribuicaoService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockConfig {

    @Bean
    DistribuicaoService distribuicaoService() {
        return Mockito.mock(DistribuicaoService.class);
    }
}
