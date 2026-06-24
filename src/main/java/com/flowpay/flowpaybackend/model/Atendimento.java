package com.flowpay.flowpaybackend.model;

import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "atendimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssuntoAtendimento assunto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAtendimento status;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime iniciadoEm;

    private LocalDateTime finalizadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id")
    private Atendente atendente;

    @PrePersist
    public void prePersist() {

        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
    }
}
