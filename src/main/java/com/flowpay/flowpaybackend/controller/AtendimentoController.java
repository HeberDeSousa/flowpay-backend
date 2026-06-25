package com.flowpay.flowpaybackend.controller;

import com.flowpay.flowpaybackend.model.dto.AtendimentoResponse;
import com.flowpay.flowpaybackend.model.dto.NovoAtendimentoRequest;
import com.flowpay.flowpaybackend.service.DistribuicaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final DistribuicaoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar atendimento")
    public AtendimentoResponse criar(@Valid @RequestBody NovoAtendimentoRequest request) {
        return service.criar(request);
    }

    @PostMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Finalizar atendimento")
    public void finalizar(@PathVariable UUID id) {
        service.finalizar(id);
    }
}