package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.MedicalRecordDto;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Medical Records", description = "Тут находятся все роуты связанные с мед записями приема")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medicalRecords")
public class MedicalRecordController {
    private final MedicalRecordService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Мед запись успешно создана.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось добавить мед запись.")
    })
    @Operation(summary = "Этот роут для создание мед записей.")
    @PostMapping
    public CustomResponseMessage<MedicalRecordDto> create(@RequestBody MedicalRecordDto recordDto) {
        try {
            return new CustomResponseMessage<>(
                    service.create(recordDto),
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
                    ResultCode.NOT_FOUND);
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    e.getClass().getSimpleName(),
                    "Ошибка сервера",
                    ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Мед запись по id успешно найден.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Мед запись по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут для поиска мед записей по id.")
    @GetMapping("/{id}")
    public CustomResponseMessage<MedicalRecordDto> getById(@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    service.getById(id),
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
                    ResultCode.NOT_FOUND);
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все доступные мед записи получены",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MedicalRecordDto.class)))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Мед записей нет"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут возвращает все доступные мед записи")
    @GetMapping()
    public CustomResponseMessage<List<MedicalRecordDto>> getAll() {
            try {
                return new CustomResponseMessage<>(
                        service.getAll(),
                        ResultCodeAPI.SUCCESS,
                        null,
                        "Все доступные мед записи успешно получены",
                        ResultCode.OK
                );
            } catch (NotFoundException exception) {
                return new CustomResponseMessage<>(
                        null,
                        ResultCodeAPI.FAIL,
                        exception.getClass().getSimpleName(),
                        exception.getMessage(),
                        ResultCode.NOT_FOUND
                );
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
                    description = "Мед запись успешно обновлена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicalRecordDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Мед запись не найдена"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось обновить мед запись.")
    })
    @Operation(summary = "Этот роут выполняет поиск мед записи по id и обновляет")
    @PutMapping("/{id}")
    public CustomResponseMessage<MedicalRecordDto> update(@PathVariable Long id, @RequestBody MedicalRecordDto recordDto) {
        try {
            return new CustomResponseMessage<>(
                    service.update(id, recordDto),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.OK);
        } catch (NotFoundException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND);
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Мед запись успешно удалена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicalRecordDto.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Мед запись не найдена"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось удалить мед запись.")
    })
    @Operation(summary = "Этот роут удаляет мед запись по id")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete(@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    service.delete(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.OK);
        } catch (NotFoundException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND);
        } catch (Exception e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR);
        }
    }

}
