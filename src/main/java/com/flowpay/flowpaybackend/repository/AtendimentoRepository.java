package com.flowpay.flowpaybackend.repository;

import com.flowpay.flowpaybackend.model.Atendimento;
import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

    long countByAtendenteIdAndStatus(UUID atendenteId, StatusAtendimento status);

    List<Atendimento> findByStatus(StatusAtendimento status);

    long countByAssuntoAndStatus(AssuntoAtendimento assunto, StatusAtendimento status);

    List<Atendimento> findByAtendenteId(UUID atendenteId);

    long countByStatus(StatusAtendimento status);
}
