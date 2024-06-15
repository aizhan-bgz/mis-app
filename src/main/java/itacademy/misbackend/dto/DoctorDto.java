package itacademy.misbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDto {
    private Long id;

    @NotBlank(message = "Поле «Имя» не может быть пустым")
    @NotNull
    private String firstName;

    @NotBlank(message = "Поле «Фамилия» не может быть пустым")
    @NotNull
    private String lastName;

    private String patronymic;
    private String specialization;
    private Long departmentId;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$",
            message = "Номер телефона должен содержать от 7 до 25 символов " +
                    "и может содержать только цифры, пробелы, скобки, дефисы и плюсы")
    @NotNull(message = "Поле «Номер телефона» не может быть пустым")
    private String phoneNumber;

    private Long userId;
 //   private List<AppointmentDto> appointments;
}
