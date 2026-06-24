package com.flowpay.flowpaybackend.model;

import com.flowpay.flowpaybackend.model.enums.TimeAtendimento;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "atendentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atendente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeAtendimento time;

}
