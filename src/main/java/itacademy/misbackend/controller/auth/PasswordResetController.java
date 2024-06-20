package itacademy.misbackend.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.UserPasswordReset;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Восстановление пароля", description = "Тут находятся роуты связанные с восстановлением пароля")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователю отправлена ссылка для сброса пароля",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Возникла ошибка.")
    })
    @Operation(summary = "Этот роут нужен для инициализации сброса пароля пользователя, " +
            "страница выходит пр нажатии на 'Забыли пароль?', " +
            "нужно ввести эл почту.")
    @PostMapping("/forgot")
    public CustomResponseMessage<Void> forgotPassword(@RequestParam String email) {
       passwordResetService.initiatePasswordReset(email);
       return new CustomResponseMessage<>(
                   null,
                   "Ожидайте сообщения с кодом, отправленное на электронную почту для сброса пароля",
                   HttpStatus.OK.value()
       );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пароль успешно изменен",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Возникла ошибка при сбросе пароля.")
    })
    @Operation(summary = "Этот роут нужен для cброса и ввода нового пароля пользователя, " +
            "пользователю приходит по почте приходит 4-х значный код, " +
            "на странице он должен ввести новый пароль.")
    @PostMapping("/reset")
    public CustomResponseMessage<Void> resetPassword(@RequestBody UserPasswordReset userPasswordReset) {

        passwordResetService.resetPassword(userPasswordReset);
        return new CustomResponseMessage<>(
                null,
                "Пароль успешно изменен. Теперь вы можете войти.",
                HttpStatus.OK.value()
        );
    }

}
