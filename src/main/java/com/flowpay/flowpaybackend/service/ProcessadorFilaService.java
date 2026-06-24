package com.flowpay.flowpaybackend.service;

import com.flowpay.flowpaybackend.model.Atendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import com.flowpay.flowpaybackend.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessadorFilaService {

    private final AtendimentoRepository atendimentoRepository;
    private final DistribuicaoService distribuicaoService;
    private final DashboardService dashboardService;
    private final DashboardPublisher publisher;

    @Scheduled(fixedDelay = 1000)
    public void processarFila() {

        List<Atendimento> aguardando =
                atendimentoRepository.findByStatus(
                        StatusAtendimento.AGUARDANDO);

        aguardando.forEach(
                distribuicaoService::distribuir
        );

        publisher.publish(
                dashboardService.obterDashboard()
        );
    }
}
