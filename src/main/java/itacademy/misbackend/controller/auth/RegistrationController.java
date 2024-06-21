package itacademy.misbackend.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.ConfirmRequest;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.UserDoctorRequest;
import itacademy.misbackend.dto.UserPatientRequest;
import itacademy.misbackend.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Registration", description = "Тут находятся все роуты связанные с регистрацией пользователя")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegistrationController {
    private final RegistrationService registrationService;
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешная регистрация(пользователь создан, но не подтвержден)",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Регистрация пользователя не удалась.")
    })
    @Operation(summary = "Этот роут для регистрации пользователей-врачей.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/doctor")
    public CustomResponseMessage<Object> registerDoctor(@Valid @RequestBody UserDoctorRequest userDoctorRequest) {
       registrationService.registerDoctor(userDoctorRequest);
       return new CustomResponseMessage<>(
                   null,
               "Регистрация прошла успешно. Проверьте почту, на которую отправлен код для подтверждения",
                   HttpStatus.CREATED.value()
       );
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешная регистрация(пользователь создан, но не подтвержден)",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Регистрация пользователя не удалась.")
    })
    @Operation(summary = "Этот роут регистрации пользователей-пациентов.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/patient")
    public CustomResponseMessage<Object> registerPatient(@Valid @RequestBody UserPatientRequest userPatientRequest) {
        registrationService.registerPatient(userPatientRequest);
        return new CustomResponseMessage<>(
                    null,
                    "Регистрация прошла успешно. Проверьте почту, на которую отправлен код для подтверждения",
                    HttpStatus.CREATED.value()
        );
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Регистрация подтверждена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Возникла ошибка при подтверждении регистрации.")
    })
    @Operation(summary = "Этот роут нужен для подтверждения регистрации пользователя, " +
            "На указанную почту приходит 4-х значный код, который нужно будет ввести пользователю")
    @PostMapping("/confirm")
    public CustomResponseMessage<Object> confirm(@RequestBody ConfirmRequest confirmRequest) {
       registrationService.confirm(confirmRequest);
       return new CustomResponseMessage<>(
               null,
               "Регистрация подтверждена, теперь вы можете войти в систему",
               HttpStatus.OK.value()
       );
    }
}
