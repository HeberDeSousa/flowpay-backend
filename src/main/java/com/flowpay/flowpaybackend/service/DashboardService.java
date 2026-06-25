package com.flowpay.flowpaybackend.service;

import com.flowpay.flowpaybackend.model.dto.DashboardResponse;
import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import com.flowpay.flowpaybackend.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AtendimentoRepository atendimentoRepository;

    public DashboardResponse obterDashboard() {

        long filaCartao =
                atendimentoRepository
                        .countByAssuntoAndStatus(
                                AssuntoAtendimento.CARTAO,
                                StatusAtendimento.AGUARDANDO);

        long filaEmprestimo =
                atendimentoRepository
                        .countByAssuntoAndStatus(
                                AssuntoAtendimento.EMPRESTIMO,
                                StatusAtendimento.AGUARDANDO);

        long filaOutros =
                atendimentoRepository
                        .countByAssuntoAndStatus(
                                AssuntoAtendimento.OUTROS,
                                StatusAtendimento.AGUARDANDO);

        long ativosCartao =
                atendimentoRepository
                        .countByAssuntoAndStatus(
                                AssuntoAtendimento.CARTAO,
                                StatusAtendimento.EM_ATENDIMENTO);

        long ativosEmprestimo =
                atendimentoRepository
                        .countByAssuntoAndStatus(
                                AssuntoAtendimento.EMPRESTIMO,
                                StatusAtendimento.EM_ATENDIMENTO);

        long ativosOutros =
                atendimentoRepository
                        .countByAssuntoAndStatus(
                                AssuntoAtendimento.OUTROS,
                                StatusAtendimento.EM_ATENDIMENTO);

        return new DashboardResponse(
                filaCartao,
                filaEmprestimo,
                filaOutros,
                ativosCartao,
                ativosEmprestimo,
                ativosOutros,
                ativosCartao + ativosEmprestimo + ativosOutros
        );
    }
}
