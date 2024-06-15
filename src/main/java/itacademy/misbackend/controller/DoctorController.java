package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.DoctorDto;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Doctors", description = "Тут находятся все роуты связанные с врачами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Врач успешно добавлен.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось добавить врача.")
    })
    @Operation(summary = "Этот роут для добавления врачей.")
    @PostMapping()
    public CustomResponseMessage<DoctorDto> create (@RequestBody DoctorDto doctorDto) {
        return new CustomResponseMessage<>(
                doctorService.save(doctorDto),
                ResultCodeAPI.CREATED,
                null,
                "success",
                ResultCode.CREATED);
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех доступных врачей получен.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Врачей не найдено."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут возвращает все доступных врачей.")
    @GetMapping()
    public CustomResponseMessage<List<DoctorDto>> getAll () {
        return new CustomResponseMessage<>(
                doctorService.getAll(),
                ResultCodeAPI.SUCCESS,
                null,
                "Список всех доступных врачей получен",
                ResultCode.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Врач по id успешно найден.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Врач по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут для поиска врачей по id.")
    @GetMapping("/{id}")
    public CustomResponseMessage<DoctorDto> getById (@PathVariable Long id) {
        return new CustomResponseMessage<>(
                doctorService.getById(id),
                ResultCodeAPI.SUCCESS,
                null,
                "Врач успешно найден",
                ResultCode.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Врач успешно обновлен",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Врач по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось обновить.")
    })
    @Operation(summary = "Этот роут для обновления врачей по id.")
    @PutMapping("/{id}")
    public CustomResponseMessage<DoctorDto> update (@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        return new CustomResponseMessage<>(
                doctorService.update(id, doctorDto),
                ResultCodeAPI.SUCCESS,
                null,
                "Врач успешно обновлен",
                ResultCode.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Врач успешно удален.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Врач по этой id не найден."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось удалить.")
    })
    @Operation(summary = "Этот роут для удаления врачей по id.")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete (@PathVariable Long id) {
        return new CustomResponseMessage<>(
                doctorService.delete(id),
                ResultCodeAPI.SUCCESS,
                null,
                "Врач успешно удален",
                ResultCode.OK);
    }
}
