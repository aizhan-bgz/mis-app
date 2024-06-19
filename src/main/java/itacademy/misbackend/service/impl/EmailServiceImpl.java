package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.EmailMessage;
import itacademy.misbackend.service.EmailService;
import itacademy.misbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    public void sendRegistrationMessage(EmailMessage message) throws MailException {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("t86501540@gmail.com");
        emailMessage.setTo(message.getEmail());
        emailMessage.setSubject("Подтверждение регистрации");
        emailMessage.setText("Для подтверждения регистрации введите 4-х значный код, " +
                             "который представлен ниже:\n" +  message.getConfirmCode());
        mailSender.send(emailMessage);
    }

    public void sendPasswordResetMessage(EmailMessage message) throws MailException {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("t86501540@gmail.com");
        emailMessage.setTo(message.getEmail());
        emailMessage.setSubject("Восстановление пароля");
        emailMessage.setText("Вы запросили восстановление пароля для вашей учетной записи. " +
                             "Пожалуйста, введите 4-х значный код, чтобы сбросить пароль:\n"
                             + message.getConfirmCode());
        mailSender.send(emailMessage);
    }
}
