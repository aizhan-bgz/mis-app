package itacademy.misbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordReset {
    private String email;
    private String confirmCode;
    private String newPassword;

}
