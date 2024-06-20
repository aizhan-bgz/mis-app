package itacademy.misbackend.mapper.impl;

import itacademy.misbackend.dto.UserDto;
import itacademy.misbackend.entity.User;
import itacademy.misbackend.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        UserDto dto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .build();
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles());
        }
        return dto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .isEnabled(userDto.isEnabled())
                .confirmCode(userDto.getConfirmCode())
                .build();
        return user;
    }

    @Override
    public List<UserDto> toDtoList(List<User> user) {
        var dtos = new ArrayList<UserDto>();
        for (User u : user) {
            dtos.add(toDto(u));
        }
        return dtos;
    }
}
