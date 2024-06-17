package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.AppointmentDto;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Appointments", description = "Тут находятся все роуты связанные с записями приема")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Прием успешно создан.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось добавить запись приема.")
    })
    @Operation(summary = "Этот роут для создания записей приема.")
    @PostMapping()
    public CustomResponseMessage<AppointmentDto> create (@RequestBody AppointmentDto appointmentDto) {
        try {
            return new CustomResponseMessage<>(
                    appointmentService.create(appointmentDto),
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
                    description = "Список всех доступных приемов получен.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут возвращает все доступные записи приемов.")
    @GetMapping()
    public CustomResponseMessage<List<AppointmentDto>> getAll () {
        try {
            return new CustomResponseMessage<>(
                    appointmentService.getAll(),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Список всех доступных приемов получен",
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
                    description = "Запись приема по id успешно найдена.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись приема по этой id не найдена."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут для поиска приема по id.")
    @GetMapping("/{id}")
    public CustomResponseMessage<AppointmentDto> getById (@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    appointmentService.getById(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Запись приема успешно найден",
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
                    description = "Запись приема успешно обновлена.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись приема по этой id не найдена."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось обновить.")
    })
    @Operation(summary = "Этот роут для обновления приема по id.")
    @PutMapping("/{id}")
    public CustomResponseMessage<AppointmentDto> update (@PathVariable Long id, @RequestBody AppointmentDto appointmentDto) {
        try {
            return new CustomResponseMessage<>(
                    appointmentService.update(id, appointmentDto),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Запись приема успешно обновлена",
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
                    description = "Запись приема успешно удалена.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись приема по этой id не найдена."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось удалить.")
    })
    @Operation(summary = "Этот роут для удаления приема по id.")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete (@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    appointmentService.delete(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "Запись приема успешно удалена",
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
