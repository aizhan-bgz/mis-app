package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.AppointmentDto;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CustomResponseMessage<AppointmentDto> create (@RequestBody AppointmentDto appointmentDto) {
        return new CustomResponseMessage<>(
                    appointmentService.create(appointmentDto),
                    "Прием успешно создан.",
                    HttpStatus.CREATED.value()
        );
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
        return new CustomResponseMessage<>(
                    appointmentService.getAll(),
                    "Список всех доступных приемов получен.",
                    HttpStatus.OK.value()
        );
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
        return new CustomResponseMessage<>(
                    appointmentService.getById(id),
                    "Запись приема по id успешно найдена.",
                    HttpStatus.OK.value()
        );
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
        return new CustomResponseMessage<>(
                    appointmentService.update(id, appointmentDto),
                    "Запись приема успешно обновлена.",
                    HttpStatus.OK.value()
        );

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
        return new CustomResponseMessage<>(
                    appointmentService.delete(id),
                    null,
                    HttpStatus.OK.value()
        );
    }
}
