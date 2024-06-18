package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.ServiceTypeDto;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Services", description = "Тут находятся все роуты связанные с услугами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
public class ServiceTypeController {
    private final ServiceTypeService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Услуга успешно создана.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось добавить услугу.")
    })
    @Operation(summary = "Этот роут для создания услуг.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomResponseMessage<ServiceTypeDto> create(@RequestBody ServiceTypeDto dto) {
        return new CustomResponseMessage<>(
                service.create(dto),
                "Услуга успешно создана.",
                HttpStatus.CREATED.value()
        );
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все доступные услуги успешно получены.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Доступных услуг нет."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут возвращает все доступные услуги.")
    @GetMapping()
    public CustomResponseMessage<List<ServiceTypeDto>> getAll() {
        return new CustomResponseMessage<>(
                service.getAll(),
                "Все доступные услуги успешно получены.",
                HttpStatus.OK.value()
        );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Услуга по id успешно найдена.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Услуга по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут для поиска услуг по id.")
    @GetMapping("/{id}")
    public CustomResponseMessage<ServiceTypeDto> getById(@PathVariable Long id) {
        return new CustomResponseMessage<>(
                service.getById(id),
                "Услуга по указанному id найдена.",
                HttpStatus.OK.value()
        );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Услуга найдена и успешно обновлена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Услуга не найдена"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось обновить услугу.")
    })
    @Operation(summary = "Этот роут выполняет поиск услуги по id и обновляет.")
    @PutMapping("/{id}")
    public CustomResponseMessage<ServiceTypeDto> update(@PathVariable Long id, @RequestBody ServiceTypeDto dto) {
      return new CustomResponseMessage<>(
              service.update(id, dto),
              "Услуга успешно обновлена",
              HttpStatus.OK.value()
      );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Услуга успешно удалена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Услуга не найдена"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось удалить услугу.")
    })
    @Operation(summary = "Этот роут удаляет услугу по id.")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete(@PathVariable Long id) {
        return new CustomResponseMessage<>(
                service.delete(id),
                null,
                HttpStatus.OK.value()
        );
    }

}
