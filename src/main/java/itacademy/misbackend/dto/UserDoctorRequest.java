package itacademy.misbackend.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDoctorRequest {
    @Valid
    private UserDto user;

    @Valid
    private DoctorDto doctor;
}
