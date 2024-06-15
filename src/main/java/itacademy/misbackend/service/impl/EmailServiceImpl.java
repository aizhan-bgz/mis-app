package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.RegistrationMessage;
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

    public void sendRegistrationMessage(RegistrationMessage message) throws MailException {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("t86501540@gmail.com"); // явно указать
        emailMessage.setTo(message.getEmail());
        emailMessage.setSubject("Подтверждение профиля");
        emailMessage.setText("Для подтверждения профиля пройдите по ссылке ниже:\n"
                + "http://localhost:9090/api/register/confirm?token=" + message.getVerificationToken()
                + "&id=" + userService.getUserIdByEmail(message.getEmail()));
        mailSender.send(emailMessage);
    }
}
