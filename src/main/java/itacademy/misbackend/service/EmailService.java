package itacademy.misbackend.service;

import itacademy.misbackend.dto.EmailMessage;
import org.springframework.scheduling.annotation.Async;
@Async
public interface EmailService {
    void sendRegistrationMessage(EmailMessage message);
    void sendPasswordResetMessage(EmailMessage message);

}
