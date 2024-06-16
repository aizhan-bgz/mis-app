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
    private final UserService userService;

    public void sendRegistrationMessage(EmailMessage message) throws MailException {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("t86501540@gmail.com");
        emailMessage.setTo(message.getEmail());
        emailMessage.setSubject("Подтверждение профиля");
        emailMessage.setText("Для подтверждения профиля пройдите по ссылке ниже:\n"
                + "https://it-academy-mis-app-eb8b8e2f87d7.herokuapp.com/api/register/confirm?token=" + message.getVerificationToken()
                + "&id=" + userService.getUserIdByEmail(message.getEmail()));
        mailSender.send(emailMessage);
    }

    public void sendPasswordResetMessage(EmailMessage message) throws MailException {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("t86501540@gmail.com");
        emailMessage.setTo(message.getEmail());
        emailMessage.setSubject("Восстановление пароля");
        emailMessage.setText("Вы запросили восстановление пароля для вашей учетной записи. " +
                "Пожалуйста, перейдите по ссылке ниже, чтобы сбросить пароль:\n:\n"
                + "https://it-academy-mis-app-eb8b8e2f87d7.herokuapp.com/api/password/reset?token=" + message.getVerificationToken()
                + "&id=" + userService.getUserIdByEmail(message.getEmail()));
        mailSender.send(emailMessage);
    }
}
