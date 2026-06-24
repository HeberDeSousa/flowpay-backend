package com.flowpay.flowpaybackend.controller;

import com.flowpay.flowpaybackend.model.dto.DashboardResponse;
import com.flowpay.flowpaybackend.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Dashboard de monitoramento")
    public DashboardResponse dashboard() {
        return dashboardService.obterDashboard();
    }
}
