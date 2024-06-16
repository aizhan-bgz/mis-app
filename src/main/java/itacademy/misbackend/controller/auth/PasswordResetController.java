package itacademy.misbackend.controller.auth;

import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot")
    public CustomResponseMessage<Void> forgotPassword(@RequestParam String email) {
       try {
           passwordResetService.initiatePasswordReset(email);
           return new CustomResponseMessage<>(
                   null,
                   ResultCodeAPI.SUCCESS,
                   "Ожидайте сообщение для сброса пароля на почту, которую вы указали",
                   null,
                   ResultCode.OK);
       } catch (NotFoundException e) {
           return new CustomResponseMessage<>(
                   null,
                   ResultCodeAPI.FAIL,
                   "Ошибка",
                   e.getMessage(),
                   ResultCode.NOT_FOUND
           );
       }
    }

    @PostMapping("/reset")
    public CustomResponseMessage<Void> resetPassword(@RequestParam String token, @RequestParam Long id, @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, id, newPassword);
        return new CustomResponseMessage<>(
                null,
                ResultCodeAPI.SUCCESS,
                "Пароль успешно изменен",
                null,
                ResultCode.OK);
    }
}
