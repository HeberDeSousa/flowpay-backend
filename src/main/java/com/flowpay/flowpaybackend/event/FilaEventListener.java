package com.flowpay.flowpaybackend.event;

import com.flowpay.flowpaybackend.service.ProcessadorFilaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FilaEventListener {

    private final ProcessadorFilaService processadorFilaService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAtendimentoCriado(AtendimentoCriadoEvent event) {
        processadorFilaService.processarFila();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAtendimentoFinalizado(AtendimentoFinalizadoEvent event) {
        processadorFilaService.processarFila();
    }
}
