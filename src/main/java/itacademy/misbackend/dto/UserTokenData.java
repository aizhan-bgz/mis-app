package itacademy.misbackend.dto;

import itacademy.misbackend.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserTokenData {
    private Long id;
    private String username;
    private Set<Role> roles;

}
