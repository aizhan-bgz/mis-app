package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Patients", description = "Тут находятся все роуты связанные с пациентами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Пациент успешно добавлен.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось добавить пациента.")
    })
    @Operation(summary = "Этот роут для добавления пациентов.")
    @PostMapping()
    public CustomResponseMessage<PatientDto> create (@RequestBody PatientDto patientDto) {
        try {
            return new CustomResponseMessage<>(
                    patientService.create(patientDto),
                    ResultCodeAPI.CREATED,
                    null,
                    "success",
                    ResultCode.CREATED);
        } catch (NotFoundException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех доступных пациентов получен.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пациентов не найдено."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут возвращает все доступных пациентов.")
    @GetMapping()
    public CustomResponseMessage<List<PatientDto>> getAll () {
        try {
            return new CustomResponseMessage<>(
                    patientService.getAll(),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Список всех доступных пациентов получен",
                    ResultCode.OK);
        } catch (NotFoundException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
        }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пациент по id успешно найден.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пациент по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут для поиска пациентов по id.")
    @GetMapping("/{id}")
    public CustomResponseMessage<PatientDto> getById (@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    patientService.getById(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Пациент успешно найден",
                    ResultCode.OK);
        } catch (NotFoundException exception) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    exception.getClass().getSimpleName(),
                    exception.getMessage(),
                    ResultCode.NOT_FOUND);
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    e.getClass().getSimpleName(),
                    "Ошибка сервера",
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пациент успешно обновлен",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пациент по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось обновить.")
    })
    @Operation(summary = "Этот роут для обновления пациентов по id.")
    @PutMapping("/{id}")
    public CustomResponseMessage<PatientDto> update (@PathVariable Long id, @RequestBody PatientDto patientDto) {
        try {
            return new CustomResponseMessage<>(
                    patientService.update(id, patientDto),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Пациент успешно обновлен",
                    ResultCode.OK);
        } catch (NotFoundException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пациент успешно удален",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пациент по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось удалить.")
    })
    @Operation(summary = "Этот роут для удаления пациентов по id.")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete (@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    patientService.delete(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Пациент успешно удален",
                    ResultCode.OK);
        } catch (NotFoundException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
