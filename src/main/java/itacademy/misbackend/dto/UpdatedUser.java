package itacademy.misbackend.dto;

import itacademy.misbackend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedUser {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<Role> roles;
}
