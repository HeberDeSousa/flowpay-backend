package com.flowpay.flowpaybackend.model.dto;

import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NovoAtendimentoRequest(

        @NotBlank
        String cliente,

        @NotNull
        AssuntoAtendimento assunto

) {
}
