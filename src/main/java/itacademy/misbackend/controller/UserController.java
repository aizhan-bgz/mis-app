package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.UserDto;
import itacademy.misbackend.dto.UserTokenData;
import itacademy.misbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Users", description = "Тут находятся все роуты связанные с пользователями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @ApiResponses( value = {

            @ApiResponse(
                responseCode = "200",
                description = "Роль успешно добавлена к пользователю"
             ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь или роль не найдены"
            )
        }
    )
    @Operation(summary = "Роут для добавления роли к пользователю")
    @PutMapping("/addRole")
    public CustomResponseMessage<Void> addRoleToUser(@RequestParam Long userId, @RequestParam String roleName) {
        userService.addRole(userId, roleName);
        return new CustomResponseMessage<>(
                null,
                "Роль успешно добавлена",
                HttpStatus.OK.value()
        );
    }

    @ApiResponses( value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден"
            )
        }
    )
    @Operation(summary = "Этот роут нужен для того, чтобы получить пользователя по id")
    @GetMapping("/{id}")
    public CustomResponseMessage<UserDto> getById(@PathVariable Long id){
        return new CustomResponseMessage<>(
                userService.getById(id),
                "Пользователь найден по указанному id",
                HttpStatus.OK.value()
        );
    }

    @ApiResponses( value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователи успешно получены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список пользователей пуст"
            )
        }
    )
    @Operation(summary = "Этот роут нужен для того, чтобы получить список всех пользователей")
    @GetMapping()
    public CustomResponseMessage<List<UserDto>> getAll(){
        return new CustomResponseMessage<>(
                userService.getAll(),
                "Список пользователей найден",
                HttpStatus.OK.value()
        );
    }

    @ApiResponses( value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден"
            )
        }
    )
    @Operation(summary = "Этот роут нужен для того, чтобы удалить пользователя. Удаляет вместе с пользователем сущность, привязанную к нему(доктор)")
    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete (@PathVariable Long id) {
        return new CustomResponseMessage<>(
                userService.delete(id),
                null,
                HttpStatus.OK.value()
        );
    }
    @ApiResponses( value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Данные успешно получены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден"
            )
        }
    )
    @Operation(summary = "Этот роут нужен для того, чтобы получить данные пользователя по токену")
    @GetMapping("/byToken")
    public CustomResponseMessage<UserTokenData> getUserFromToken (@RequestParam String token){
        return new CustomResponseMessage<>(
                userService.getUserFromToken(token),
                null,
                HttpStatus.OK.value()
        );
    }
}
