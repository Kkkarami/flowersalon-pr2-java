package com.fedelesh.flowersalon.infrastructure.email;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class SmtpEmailSender implements EmailSender {

    @Override
    public void send(String to, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("andrey.fedelesh12@gmail.com",
                      "wmtfvxgddxxahhbj");
            }
        });

        try {
            Message mime = new MimeMessage(session);
            mime.setFrom(new InternetAddress("admin@flowersalon.com"));
            mime.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mime.setSubject(subject);
            mime.setText(message);

            Transport.send(mime);
        } catch (Exception e) {
            throw new RuntimeException("Email error: " + e.getMessage());
        }
    }
}
