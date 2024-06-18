package itacademy.misbackend.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.UserDoctorRequest;
import itacademy.misbackend.dto.UserPatientRequest;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Registration", description = "Тут находятся все роуты связанные с пациентами")
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
               "Successful registration. Here is Verification Token: " +
                        userDoctorRequest.getUser().getVerificationToken() + " User id: " +
                        userDoctorRequest.getUser().getId(),
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
                    "Successful registration. Here is Verification Token: " +
                            userPatientRequest.getUser().getVerificationToken() + " User id: " +
                            userPatientRequest.getUser().getId(),
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
            "нужно ввести токен подтверждения и id пользователя")
    @GetMapping("/confirm")
    public CustomResponseMessage<Object> confirm(@RequestParam String token, @RequestParam Long id) {
       registrationService.confirm(token, id);
       return new CustomResponseMessage<>(
               null,
               "Registration successfully confirmed",
               HttpStatus.OK.value()
       );
    }
}
