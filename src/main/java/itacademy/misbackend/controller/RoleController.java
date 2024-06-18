package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.entity.Role;
import itacademy.misbackend.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Roles", description = "Здесь находятся роуты для работы с ролями")
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CustomResponseMessage<Long> save(@Valid @RequestBody Role role) {
        return new CustomResponseMessage<>(
                service.save(role),
                "Роль успешно сохранена.",
                HttpStatus.CREATED.value()
        );
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
        return new CustomResponseMessage<>(
                    service.findAll(),
                    "All roles successfully received",
                    HttpStatus.OK.value()
        );
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Роль по id успешно получена",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Роль по указанному id не найдена")
    })
    @Operation(summary = "Этот роут возвращает роли по айди")
    @GetMapping("/{id}")
    public CustomResponseMessage<Role> getById(@PathVariable Long id) {
        return new CustomResponseMessage<>(
                    service.findById(id),
                    "Роль по id успешно получена",
                    HttpStatus.OK.value()
        );
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
        return new CustomResponseMessage<>(
                service.update(id, role),
                "Роль успешно обновлена",
                HttpStatus.OK.value()
        );
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
       return new CustomResponseMessage<>(
               service.delete(id),
               null,
               HttpStatus.OK.value()
       );
    }
}
