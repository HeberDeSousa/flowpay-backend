package com.flowpay.flowpaybackend.repository;

import com.flowpay.flowpaybackend.model.Atendente;
import com.flowpay.flowpaybackend.model.enums.TimeAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AtendenteRepository extends JpaRepository<Atendente, UUID> {

    List<Atendente> findByTime(TimeAtendimento time);

}
