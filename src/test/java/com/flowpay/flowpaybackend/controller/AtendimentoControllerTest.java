package com.flowpay.flowpaybackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowpay.flowpaybackend.config.MockConfig;
import com.flowpay.flowpaybackend.model.dto.AtendimentoResponse;
import com.flowpay.flowpaybackend.model.dto.NovoAtendimentoRequest;
import com.flowpay.flowpaybackend.model.enums.AssuntoAtendimento;
import com.flowpay.flowpaybackend.model.enums.StatusAtendimento;
import com.flowpay.flowpaybackend.service.DistribuicaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AtendimentoController.class)
@Import(MockConfig.class)
class AtendimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DistribuicaoService service;

    @Test
    void deveCriarAtendimento() throws Exception {

        NovoAtendimentoRequest request =
                new NovoAtendimentoRequest(
                        "José",
                        AssuntoAtendimento.CARTAO
                );

        AtendimentoResponse response =
                new AtendimentoResponse(
                        UUID.randomUUID(),
                        "José",
                        AssuntoAtendimento.CARTAO,
                        StatusAtendimento.EM_ATENDIMENTO,
                        "João",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );

        when(service.criar(request)).thenReturn(response);

        mockMvc.perform(
                        post("/api/atendimentos")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());
    }
}