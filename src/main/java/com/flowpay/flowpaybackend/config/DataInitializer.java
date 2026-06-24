package com.flowpay.flowpaybackend.config;

import com.flowpay.flowpaybackend.model.Atendente;
import com.flowpay.flowpaybackend.model.enums.TimeAtendimento;
import com.flowpay.flowpaybackend.repository.AtendenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final AtendenteRepository repository;

    @Bean
    CommandLineRunner loadData() {

        return args -> {

            if (repository.count() > 0) {
                return;
            }

            repository.save(
                    Atendente.builder()
                            .nome("At1 Cartões")
                            .time(TimeAtendimento.CARTAO)
                            .build());

            repository.save(
                    Atendente.builder()
                            .nome("At2 Cartões")
                            .time(TimeAtendimento.CARTAO)
                            .build());

            repository.save(
                    Atendente.builder()
                            .nome("At1 Empréstimos")
                            .time(TimeAtendimento.EMPRESTIMO)
                            .build());

            repository.save(
                    Atendente.builder()
                            .nome("At2 Empréstimos")
                            .time(TimeAtendimento.EMPRESTIMO)
                            .build());

            repository.save(
                    Atendente.builder()
                            .nome("At1 Outros")
                            .time(TimeAtendimento.OUTROS)
                            .build());

            repository.save(
                    Atendente.builder()
                            .nome("At2 Outros")
                            .time(TimeAtendimento.OUTROS)
                            .build());
        };
    }
}
