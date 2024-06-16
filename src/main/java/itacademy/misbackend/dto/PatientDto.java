package itacademy.misbackend.dto;

import itacademy.misbackend.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {
    private Long id;

    @NotBlank(message = "Поле «Имя» не может быть пустым")
    @NotNull
    private String firstName;

    @NotBlank(message = "Поле «Фамилия» не может быть пустым")
    @NotNull
    private String lastName;

    private String patronymic;

    @NotNull(message = "Поле «Пол» не может быть пустым")
    private Sex sex;

    @NotNull(message = "Поле «Дата рождения» не может быть пустым")
    private LocalDate dateOfBirth;

    private String passport;

    @Pattern(regexp = "^[0-9]{14}$", message = "ИНН должен содержать 14 цифр")
    @NotNull(message = "Поле «ИНН» не может быть пустым")
    private String taxId;
    private String address;

    private String placeOfWork;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$",
            message = "Номер телефона должен содержать от 7 до 25 символов " +
                    "и может содержать только цифры, пробелы, скобки, дефисы и плюсы")
    @NotNull(message = "Поле «Номер телефона» не может быть пустым")
    private String phoneNumber;

    private Long userId;

    private List<AppointmentDto> appointments;

    private LocalDate deletedAt;
    private String deletedBy;
}
