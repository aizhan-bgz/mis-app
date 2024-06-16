package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.entity.Role;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Роль успешно сохранена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось сохранить роль")
    })
    @Operation(summary = "Этот роут для создания ролей")
    @PostMapping()
    public CustomResponseMessage<Long> save(@Valid @RequestBody Role role) {
        try{
            return new CustomResponseMessage<>(
                    service.save(role),
                    ResultCodeAPI.SUCCESS,
                    "Роль успешно сохранена",
                    null,
                    ResultCode.CREATED
            );
        } catch (PropertyValueException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка: проверьте поле name",
                    e.getMessage(),
                    ResultCode.FAIL
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
                    description = "Все роли успешно получены",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Нет ни одной роли")
    })
    @Operation(summary = "Этот роут возвращает весь список ролей")
    @GetMapping()
    public CustomResponseMessage<List<Role>> getAll() {
        try {
            return new CustomResponseMessage<>(
                    service.findAll(),
                    ResultCodeAPI.SUCCESS,
                    "Все роли успешно получены",
                    null,
                    ResultCode.OK
            );
        } catch (NotFoundException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (DuplicateKeyException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.FAIL
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
                    description = "Роль по айди получены успешно",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Роль по этой айди не найден")
    })
    @Operation(summary = "Этот роут возвращает роли по айди")
    @GetMapping("/{id}")
    public CustomResponseMessage<Role> getById(@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    service.findById(id),
                    ResultCodeAPI.SUCCESS,
                    "Роль по ID успешно получен",
                    null,
                    ResultCode.OK
            );
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
                    description = "Роль успешно обновлена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось обновить роль")
    })
    @Operation(summary = "Этот роут для обновления ролей " +
                         "Обновляет поле name")
    @PutMapping("/{id}")
    public CustomResponseMessage<Role> update(@PathVariable Long id, @RequestBody Role role) {
        try {
            return new CustomResponseMessage<>(
                    service.update(id, role),
                    ResultCodeAPI.SUCCESS,
                    "Роль успешно обновлена",
                    null,
                    ResultCode.OK
            );
        } catch (PropertyValueException e) {
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка: проверьте поле name",
                    e.getMessage(),
                    ResultCode.FAIL
            );
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
                    description = "Роль успешно удалена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось удалить роль")
    })
    @Operation(summary = "Этот роут для удаление ролей")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete(@PathVariable Long id) {
        try {
            return new CustomResponseMessage<>(
                    service.delete(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    null,
                    ResultCode.OK
            );
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
