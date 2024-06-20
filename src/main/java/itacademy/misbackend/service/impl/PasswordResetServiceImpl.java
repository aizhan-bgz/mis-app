package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.EmailMessage;
import itacademy.misbackend.entity.User;
import itacademy.misbackend.exception.ConfirmationCodeMismatchException;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.repo.UserRepo;
import itacademy.misbackend.service.EmailService;
import itacademy.misbackend.service.PasswordResetService;
import itacademy.misbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepo userRepo;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void initiatePasswordReset(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Пользователь с таким email не найден");
        }
        user.setConfirmCode(userService.generateConfirmationCode());
        userRepo.save(user);

        EmailMessage message = new EmailMessage();
        message.setEmail(user.getEmail());
        message.setConfirmCode(user.getConfirmCode());
        emailService.sendPasswordResetMessage(message);
    }

    public void resetPassword(String confirmCode, Long id, String newPassword) {
        User user = userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (!user.getConfirmCode().equals(confirmCode)) {
            throw new ConfirmationCodeMismatchException("Неверный код подтверждения");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }



}
