package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.UserDto;
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

    @PutMapping("/addRole")
    public CustomResponseMessage<Void> addRoleToUser(@RequestParam Long userId, @RequestParam String roleName) {
        userService.addRole(userId, roleName);
        return new CustomResponseMessage<>(
                null,
                "Роль успешно добавлена",
                HttpStatus.OK.value()
        );
    }

    @GetMapping("/{id}")
    public CustomResponseMessage<UserDto> getById(@PathVariable Long id){
        return new CustomResponseMessage<>(
                userService.getById(id),
                "Пользователь найден по указанному id",
                HttpStatus.OK.value()
        );
    }

    @GetMapping()
    public CustomResponseMessage<List<UserDto>> getAll(){
        return new CustomResponseMessage<>(
                userService.getAll(),
                "Список пользователей найден",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete (@PathVariable Long id) {
        return new CustomResponseMessage<>(
                userService.delete(id),
                null,
                HttpStatus.OK.value()
        );
    }
}
