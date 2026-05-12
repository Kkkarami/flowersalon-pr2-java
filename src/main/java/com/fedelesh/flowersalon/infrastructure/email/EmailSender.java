package com.fedelesh.flowersalon.infrastructure.email;

public interface EmailSender {

    void send(String to, String subject, String message);
}
