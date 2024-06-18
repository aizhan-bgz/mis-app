package itacademy.misbackend.controller.auth;

import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot")
    public CustomResponseMessage<Void> forgotPassword(@RequestParam String email) {
       passwordResetService.initiatePasswordReset(email);
       return new CustomResponseMessage<>(
                   null,
                   "Wait for a password reset message to the email you provided.",
                   HttpStatus.OK.value()
       );
    }

    @PostMapping("/reset")
    public CustomResponseMessage<Void> resetPassword(@RequestParam String token, @RequestParam Long id, @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, id, newPassword);
        return new CustomResponseMessage<>(
                null,
                "Password changed successfully",
                HttpStatus.OK.value()
        );
    }
}
