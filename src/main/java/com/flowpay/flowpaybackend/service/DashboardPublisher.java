package com.flowpay.flowpaybackend.service;

import com.flowpay.flowpaybackend.model.dto.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publish(DashboardResponse dashboard) {

        messagingTemplate.convertAndSend(
                "/topic/dashboard",
                dashboard
        );
    }
}
