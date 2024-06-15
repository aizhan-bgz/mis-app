package itacademy.misbackend.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.UserDoctorRequest;
import itacademy.misbackend.dto.UserPatientRequest;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/doctor")
    public CustomResponseMessage<Void> registerDoctor(@Valid @RequestBody UserDoctorRequest userDoctorRequest) {
       try{
           registrationService.registerDoctor(userDoctorRequest);
           return new CustomResponseMessage(
                   null,
                   ResultCodeAPI.CREATED,
                   "Ожидайте сообщение для подтверждения регистрации на почту, которую вы указали при регистрации",
                   null,
                   ResultCode.CREATED);
       } catch (NotFoundException e) {
           return new CustomResponseMessage(
                   null,
                   ResultCodeAPI.FAIL,
                   "Ошибка",
                   e.getMessage(),
                   ResultCode.NOT_FOUND
           );
       } catch (Exception e) {
           return new CustomResponseMessage(
                   null,
                   ResultCodeAPI.EXCEPTION,
                   "Ошибка на стороне сервера",
                   e.getMessage(),
                   ResultCode.INTERNAL_SERVER_ERROR
           );
       }
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
    @PostMapping("/patient")
    public CustomResponseMessage<Void> registerPatient(@Valid @RequestBody UserPatientRequest userPatientRequest) {
        try{
            registrationService.registerPatient(userPatientRequest);
            return new CustomResponseMessage(
                    null,
                    ResultCodeAPI.CREATED,
                    "Ожидайте сообщение для подтверждения регистрации на почту, которую вы указали при регистрации",
                    null,
                    ResultCode.CREATED);
        } catch (NotFoundException e) {
            return new CustomResponseMessage(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e) {
            return new CustomResponseMessage(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка на стороне сервера",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
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
    @Operation(summary = "Этот роут нужен для подтверждения регистрации пользователя, приходит пользователю по почте.")
    @GetMapping("/confirm")
    public CustomResponseMessage<Void> confirm(@RequestParam String token, @RequestParam Long id) {
        try{
            registrationService.confirm(token, id);
            return new CustomResponseMessage(
                    null,
                    ResultCodeAPI.SUCCESS,
                    "Регистрация успешно подтверждена",
                    null,
                    ResultCode.OK);
        } catch (NotFoundException e) {
            return new CustomResponseMessage(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e) {
            return new CustomResponseMessage(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка на стороне сервера",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
