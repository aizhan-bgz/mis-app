package itacademy.misbackend.dto;

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
