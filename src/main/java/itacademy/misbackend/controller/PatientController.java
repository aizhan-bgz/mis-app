package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CustomResponseMessage<PatientDto> create (@RequestBody PatientDto patientDto) {
        return new CustomResponseMessage<>(
                patientService.create(patientDto),
                "Пациент успешно добавлен.",
                HttpStatus.CREATED.value()
        );
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
        return new CustomResponseMessage<>(
                patientService.getAll(),
                "Список всех доступных пациентов получен.",
                HttpStatus.OK.value()
        );
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
        return new CustomResponseMessage<>(
                patientService.getById(id),
                "Пациент по id успешно найден.",
                HttpStatus.OK.value());
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
        return new CustomResponseMessage<>(
                    patientService.update(id, patientDto),
                    "Пациент успешно обновлен",
                    HttpStatus.OK.value()
        );
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
        return new CustomResponseMessage<>(
                    patientService.delete(id),
                null,
                    HttpStatus.OK.value()
        );
    }
}
