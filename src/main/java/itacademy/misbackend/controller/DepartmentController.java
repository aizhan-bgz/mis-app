package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.DepartmentDto;
import itacademy.misbackend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Departments", description = "Тут находятся все роуты связанные с отделениями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Отделение успешно создано.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось добавить отделение.")
    })
    @Operation(summary = "Этот роут для создания отделений.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CustomResponseMessage<DepartmentDto> create (@RequestBody DepartmentDto departmentDto) {
        return new CustomResponseMessage<>(
                departmentService.create(departmentDto),
                "Отделение успешно создано..",
                HttpStatus.CREATED.value());
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех доступных отделений получен.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отделений не найдено."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут возвращает все доступные отделения.")
    @GetMapping()
    public CustomResponseMessage<List<DepartmentDto>> getAll () {
        return new CustomResponseMessage<>(
                departmentService.getAll(),
                "Список всех доступных отделений получен.",
                HttpStatus.OK.value()
        );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отделение по id успешно найдено.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отделение по этой id не найдено."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут для поиска отделений по id.")
    @GetMapping("/{id}")
    public CustomResponseMessage<DepartmentDto> getById (@PathVariable Long id) {
        return new CustomResponseMessage<>(
                departmentService.getById(id),
                "Отделение по id успешно найдено.",
                HttpStatus.OK.value()
        );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отделение по id успешно обновлено.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отделение по этой id не найдено."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось обновить.")
    })
    @Operation(summary = "Этот роут для поиска отделений по idи ее обновления.")
    @PutMapping("/{id}")
    public CustomResponseMessage<DepartmentDto> update (@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        return new CustomResponseMessage<>(
                departmentService.update(id, departmentDto),
                "Отделение по id успешно обновлено.",
                HttpStatus.OK.value()
        );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отделение по id успешно удалено.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отделение по этой id не найдено."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось удалить.")
    })
    @Operation(summary = "Этот роут для удаления отделений по id.")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete (@PathVariable Long id) {
        return new CustomResponseMessage<>(
                departmentService.delete(id),
                null,
                HttpStatus.OK.value()
        );
    }
}
