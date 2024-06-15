package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.ServiceTypeDto;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
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
    @PostMapping
    public CustomResponseMessage<ServiceTypeDto> create(@RequestBody ServiceTypeDto dto) {
        try {
            return new CustomResponseMessage<>(
                    service.create(dto),
                    ResultCodeAPI.CREATED,
                    null,
                    "success",
                    ResultCode.CREATED
            );
        }catch (NotFoundException exception) {
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
                    ResultCode.FAIL
            );
        }
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
        try {
            return new CustomResponseMessage<>(
                    service.getAll(),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
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
                    ResultCode.FAIL
            );
        }
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
        try {
            return new CustomResponseMessage<>(
                    service.getById(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
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
                    ResultCode.FAIL
            );
        }
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
    @PutMapping("/update")
    public CustomResponseMessage<ServiceTypeDto> update(@RequestParam Long id, @RequestBody ServiceTypeDto dto) {
            try {
                return new CustomResponseMessage<>(
                        service.update(id, dto),
                        ResultCodeAPI.SUCCESS,
                        null,
                        "success",
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
                        ResultCode.FAIL
                );
            }
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
    @PutMapping("/delete")
    public CustomResponseMessage<String> delete(@RequestParam Long id) {
        try {
            return new CustomResponseMessage<>(
                    service.delete(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
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
                    ResultCode.FAIL);
        }
    }

}
