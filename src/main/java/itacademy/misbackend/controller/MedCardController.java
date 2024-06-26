package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.MedCardDto;
import itacademy.misbackend.dto.MedicalRecordDto;
import itacademy.misbackend.service.MedCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Medical cards", description = "Тут находятся все роуты связанные с мед картами пациентов")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medCards")
public class MedCardController {
    private final MedCardService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все доступные мед карты получены",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MedicalRecordDto.class)))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Мед карт нет"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут возвращает все доступные мед карты")
    @GetMapping()
    public CustomResponseMessage<List<MedCardDto>> getAll() {
        return new CustomResponseMessage<>(
                service.getAll(),
                "Все доступные мед карты получены",
                HttpStatus.OK.value()
        );
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Мед карта по id успешно найдена.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Мед карта по этой id не найдена."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Не удалось выполнить поиск.")
    })
    @Operation(summary = "Этот роут для поиска мед карт по id.")
    @GetMapping("/{id}")
    public CustomResponseMessage<MedCardDto> getById(@PathVariable Long id) {
        return new CustomResponseMessage<>(
                service.getById(id),
                "Мед карта по id успешно найдена.",
                HttpStatus.OK.value()
        );
    }
}
