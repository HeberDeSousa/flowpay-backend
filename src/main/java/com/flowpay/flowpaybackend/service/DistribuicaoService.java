package com.flowpay.flowpaybackend.service;

import com.flowpay.flowpaybackend.event.AtendimentoCriadoEvent;
import com.flowpay.flowpaybackend.event.AtendimentoFinalizadoEvent;
import com.flowpay.flowpaybackend.model.Atendente;
import com.flowpay.flowpaybackend.model.Atendimento;
import com.flowpay.flowpaybackend.model.dto.AtendimentoResponse;
import com.flowpay.flowpaybackend.model.dto.NovoAtendimentoRequest;
import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import com.flowpay.flowpaybackend.model.enums.TimeAtendimento;
import com.flowpay.flowpaybackend.repository.AtendenteRepository;
import com.flowpay.flowpaybackend.repository.AtendimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DistribuicaoService {

    private static final int LIMITE = 3;

    private final AtendimentoRepository atendimentoRepository;
    private final AtendenteRepository atendenteRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AtendimentoResponse criar(
            NovoAtendimentoRequest request) {

        Atendimento atendimento =
                Atendimento.builder()
                        .cliente(request.cliente())
                        .assunto(request.assunto())
                        .status(StatusAtendimento.AGUARDANDO)
                        .build();

        Atendimento salvo =
                atendimentoRepository.save(atendimento);

        distribuir(salvo);

        eventPublisher.publishEvent(
                new AtendimentoCriadoEvent(salvo.getId()));

        return toResponse(
                atendimentoRepository.findById(
                        salvo.getId()).orElseThrow()
        );
    }

    public void distribuir(
            Atendimento atendimento) {

        TimeAtendimento time =
                mapearTime(
                        atendimento.getAssunto());

        List<Atendente> atendentes =
                atendenteRepository.findByTime(time);

        Atendente disponivel =
                atendentes.stream()
                        .filter(this::possuiCapacidade)
                        .min(Comparator.comparingLong(
                                this::atendimentosAtivos))
                        .orElse(null);

        if (disponivel == null) {
            return;
        }

        atendimento.setAtendente(disponivel);
        atendimento.setStatus(
                StatusAtendimento.EM_ATENDIMENTO);

        atendimento.setIniciadoEm(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        atendimentoRepository.save(atendimento);
    }

    @Transactional
    public void finalizar(UUID id) {

        Atendimento atendimento =
                atendimentoRepository.findById(id)
                        .orElseThrow(
                                EntityNotFoundException::new);

        atendimento.setStatus(
                StatusAtendimento.FINALIZADO);

        atendimento.setFinalizadoEm(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        atendimentoRepository.save(atendimento);

        eventPublisher.publishEvent(
                new AtendimentoFinalizadoEvent(id));
    }

    private boolean possuiCapacidade(
            Atendente atendente) {

        return atendimentosAtivos(atendente)
                < LIMITE;
    }

    private long atendimentosAtivos(
            Atendente atendente) {

        return atendimentoRepository
                .countByAtendenteIdAndStatus(
                        atendente.getId(),
                        StatusAtendimento.EM_ATENDIMENTO);
    }

    private TimeAtendimento mapearTime(
            AssuntoAtendimento assunto) {

        return switch (assunto) {

            case CARTAO -> TimeAtendimento.CARTAO;

            case EMPRESTIMO -> TimeAtendimento.EMPRESTIMO;

            default -> TimeAtendimento.OUTROS;
        };
    }

    private AtendimentoResponse toResponse(
            Atendimento atendimento) {

        return new AtendimentoResponse(
                atendimento.getId(),
                atendimento.getCliente(),
                atendimento.getAssunto(),
                atendimento.getStatus(),
                atendimento.getAtendente() != null
                        ? atendimento.getAtendente().getNome()
                        : null,
                atendimento.getCriadoEm(),
                atendimento.getIniciadoEm()
        );
    }
}
