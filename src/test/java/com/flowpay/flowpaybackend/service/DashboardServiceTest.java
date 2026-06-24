package com.flowpay.flowpaybackend.service;

import com.flowpay.flowpaybackend.model.dto.DashboardResponse;
import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import com.flowpay.flowpaybackend.repository.AtendimentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private AtendimentoRepository repository;

    @InjectMocks
    private DashboardService service;

    @Test
    void deveRetornarDashboard() {

        when(repository.countByAssuntoAndStatus(
                AssuntoAtendimento.CARTAO,
                StatusAtendimento.AGUARDANDO))
                .thenReturn(2L);

        when(repository.countByAssuntoAndStatus(
                AssuntoAtendimento.EMPRESTIMO,
                StatusAtendimento.AGUARDANDO))
                .thenReturn(1L);

        when(repository.countByAssuntoAndStatus(
                AssuntoAtendimento.OUTROS,
                StatusAtendimento.AGUARDANDO))
                .thenReturn(0L);

        when(repository.countByAssuntoAndStatus(
                AssuntoAtendimento.CARTAO,
                StatusAtendimento.EM_ATENDIMENTO))
                .thenReturn(3L);

        when(repository.countByAssuntoAndStatus(
                AssuntoAtendimento.EMPRESTIMO,
                StatusAtendimento.EM_ATENDIMENTO))
                .thenReturn(2L);

        when(repository.countByAssuntoAndStatus(
                AssuntoAtendimento.OUTROS,
                StatusAtendimento.EM_ATENDIMENTO))
                .thenReturn(1L);

        DashboardResponse response =
                service.obterDashboard();

        assertEquals(6, response.totalAtivos());
        assertEquals(2, response.filaCartao());
    }
}
