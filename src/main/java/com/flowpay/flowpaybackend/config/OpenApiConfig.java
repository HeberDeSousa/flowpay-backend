package com.flowpay.flowpaybackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("FlowPay API")
                                .version("1.0")
                                .description(
                                        "Sistema de Distribuição e Monitoramento de Atendimentos Flowpay"
                                )
                );
    }
}