package itacademy.misbackend.service;

import itacademy.misbackend.dto.EmailMessage;

public interface EmailService {
//    void sendRegistrationMessage(EmailMessage message);
    void sendPasswordResetMessage(EmailMessage message);

}
