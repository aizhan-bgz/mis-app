package itacademy.misbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.misbackend.dto.CustomResponseMessage;
import itacademy.misbackend.dto.UpdatedUser;
import itacademy.misbackend.dto.UserDto;
import itacademy.misbackend.enums.ResultCode;
import itacademy.misbackend.enums.ResultCodeAPI;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.service.UserService;
import lombok.RequiredArgsConstructor;
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
       try {
           userService.addRole(userId, roleName);
           return new CustomResponseMessage<>(
                   null,
                   ResultCodeAPI.SUCCESS,
                   "Роль успешно добавлена ",
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
                   "Ошибка на стороне сервера",
                   e.getMessage(),
                   ResultCode.INTERNAL_SERVER_ERROR
           );
       }
    }

    @GetMapping("/{id}")
    public CustomResponseMessage<UserDto> getById(@PathVariable Long id){
        try {
            return new CustomResponseMessage<>(
                    userService.getById(id),
                    ResultCodeAPI.SUCCESS,
                    "Пользователь найден",
                    null,
                    ResultCode.OK
            );
        } catch (NotFoundException e){
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Пользователь не найден",
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

    @GetMapping()
    public CustomResponseMessage<List<UserDto>> getAll(){
        try {
            return new CustomResponseMessage<>(
                    userService.getAll(),
                    ResultCodeAPI.SUCCESS,
                    "Список пользователей найден",
                    null,
                    ResultCode.OK
            );
        } catch (NotFoundException e){
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Список пользователей пуст",
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

    @PutMapping("/{id}")
    public CustomResponseMessage<UserDto> update (@PathVariable Long id, @RequestBody UpdatedUser userDto) {
        try{
            return new CustomResponseMessage<>(
                    userService.update(id, userDto),
                    ResultCodeAPI.SUCCESS,
                    "Пользователь успешно обновлен",
                    null,
                    ResultCode.OK
            );
        } catch (NotFoundException e){
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Пользователь не найден",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e){
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.EXCEPTION,
                    "Ошибка",
                    e.getMessage(),
                    ResultCode.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public CustomResponseMessage<String> delete (@PathVariable Long id) {
        try{
            return new CustomResponseMessage<>(
                    userService.delete(id),
                    ResultCodeAPI.SUCCESS,
                    "Пользователь успешно удален",
                    null,
                    ResultCode.OK
            );
        } catch (NotFoundException e){
            return new CustomResponseMessage<>(
                    null,
                    ResultCodeAPI.FAIL,
                    "Пользователь не найден",
                    e.getMessage(),
                    ResultCode.NOT_FOUND
            );
        } catch (Exception e){
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
