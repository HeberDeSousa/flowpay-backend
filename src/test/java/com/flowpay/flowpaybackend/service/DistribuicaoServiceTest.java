package com.flowpay.flowpaybackend.service;

import com.flowpay.flowpaybackend.model.Atendente;
import com.flowpay.flowpaybackend.model.Atendimento;
import com.flowpay.flowpaybackend.model.dto.NovoAtendimentoRequest;
import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import com.flowpay.flowpaybackend.model.enums.TimeAtendimento;
import com.flowpay.flowpaybackend.repository.AtendenteRepository;
import com.flowpay.flowpaybackend.repository.AtendimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistribuicaoServiceTest {

    @Mock
    private AtendimentoRepository atendimentoRepository;

    @Mock
    private AtendenteRepository atendenteRepository;

    @InjectMocks
    private DistribuicaoService service;

    private Atendente atendente;

    @BeforeEach
    void setup() {

        atendente =
                Atendente.builder()
                        .id(UUID.randomUUID())
                        .nome("João")
                        .time(TimeAtendimento.CARTAO)
                        .build();
    }

    @Test
    void deveDistribuirAtendimentoQuandoExisteCapacidade() {

        NovoAtendimentoRequest request =
                new NovoAtendimentoRequest(
                        "Cliente Teste",
                        AssuntoAtendimento.CARTAO
                );

        Atendimento atendimento =
                Atendimento.builder()
                        .id(UUID.randomUUID())
                        .cliente("Cliente Teste")
                        .assunto(AssuntoAtendimento.CARTAO)
                        .status(StatusAtendimento.AGUARDANDO)
                        .build();

        when(atendimentoRepository.save(any()))
                .thenReturn(atendimento);

        when(atendimentoRepository.findById(any()))
                .thenReturn(java.util.Optional.of(atendimento));

        when(atendenteRepository.findByTime(
                TimeAtendimento.CARTAO))
                .thenReturn(List.of(atendente));

        when(atendimentoRepository.countByAtendenteIdAndStatus(
                any(),
                eq(StatusAtendimento.EM_ATENDIMENTO)))
                .thenReturn(1L);

        service.criar(request);

        verify(atendimentoRepository, atLeastOnce())
                .save(any());
    }

    @Test
    void deveManterNaFilaQuandoAtendenteTemTresAtendimentos() {

        Atendimento atendimento =
                Atendimento.builder()
                        .cliente("Cliente")
                        .assunto(AssuntoAtendimento.CARTAO)
                        .status(StatusAtendimento.AGUARDANDO)
                        .build();

        when(atendenteRepository.findByTime(
                TimeAtendimento.CARTAO))
                .thenReturn(List.of(atendente));

        when(atendimentoRepository.countByAtendenteIdAndStatus(
                atendente.getId(),
                StatusAtendimento.EM_ATENDIMENTO))
                .thenReturn(3L);

        service.distribuir(atendimento);

        assertNull(atendimento.getAtendente());

        assertEquals(
                StatusAtendimento.AGUARDANDO,
                atendimento.getStatus()
        );
    }

    @Test
    void deveFinalizarAtendimento() {

        UUID id = UUID.randomUUID();

        Atendimento atendimento =
                Atendimento.builder()
                        .id(id)
                        .status(StatusAtendimento.EM_ATENDIMENTO)
                        .build();

        when(atendimentoRepository.findById(id))
                .thenReturn(java.util.Optional.of(atendimento));

        service.finalizar(id);

        assertEquals(
                StatusAtendimento.FINALIZADO,
                atendimento.getStatus()
        );

        verify(atendimentoRepository)
                .save(atendimento);
    }
}
