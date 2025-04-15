package com.example.bank.service;

import com.example.bank.DTO.AccountUpdateMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendAccountUpdate(String accountNumber, double newBalance) {
        messagingTemplate.convertAndSend(
                "/account/update",
                new AccountUpdateMessage(accountNumber, newBalance)
        );
    }
}