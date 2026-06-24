package com.flowpay.flowpaybackend.model.dto;

import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;

import java.time.LocalDateTime;
import java.util.UUID;

public record AtendimentoResponse(
        UUID id,
        String cliente,
        AssuntoAtendimento assunto,
        StatusAtendimento status,
        String atendente,
        LocalDateTime criadoEm,
        LocalDateTime iniciadoEm

) {
}