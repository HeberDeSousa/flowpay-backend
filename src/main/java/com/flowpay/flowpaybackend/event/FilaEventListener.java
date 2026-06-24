package com.flowpay.flowpaybackend.event;

import com.flowpay.flowpaybackend.service.ProcessadorFilaService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilaEventListener {

    private final ProcessadorFilaService processadorFilaService;

    @EventListener
    public void onAtendimentoCriado(AtendimentoCriadoEvent event) {
        processadorFilaService.processarFila();
    }

    @EventListener
    public void onAtendimentoFinalizado(AtendimentoFinalizadoEvent event) {
        processadorFilaService.processarFila();
    }
}
