package itacademy.misbackend;

import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.dto.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPatientRequest {
    @Valid
    UserDto user;

    @Valid
    PatientDto patient;
}
