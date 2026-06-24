package com.flowpay.flowpaybackend.controller;

import com.flowpay.flowpaybackend.model.dto.AtendimentoResponse;
import com.flowpay.flowpaybackend.model.dto.NovoAtendimentoRequest;
import com.flowpay.flowpaybackend.service.DistribuicaoService;
import com.flowpay.flowpaybackend.service.ProcessadorFilaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final DistribuicaoService service;

    @Autowired
    ProcessadorFilaService processadorFilaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar atendimento")
    public AtendimentoResponse criar(@Valid @RequestBody NovoAtendimentoRequest request) {
        AtendimentoResponse response = service.criar(request);
        processadorFilaService.processarFila();
        return response;
    }

    @PostMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Finalizar atendimento")
    public void finalizar(@PathVariable UUID id) {
        service.finalizar(id);
        processadorFilaService.processarFila();
    }
}