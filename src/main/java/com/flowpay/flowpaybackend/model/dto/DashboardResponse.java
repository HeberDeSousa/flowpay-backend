package com.flowpay.flowpaybackend.model.dto;

public record DashboardResponse(
        long filaCartao,
        long filaEmprestimo,
        long filaOutros,

        long ativosCartao,
        long ativosEmprestimo,
        long ativosOutros,

        long totalAtivos

) {
}