package itacademy.misbackend.service;

import itacademy.misbackend.dto.*;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);
    UserDoctorRequest createDoctor(UserDoctorRequest userDoctorRequest);
    UserPatientRequest createPatient(UserPatientRequest userPatientRequest);
    UserDto getById(Long id);
    List<UserDto> getAll();
    void addRole(Long id, String role);
    String delete(Long id);

    Long getUserIdByEmail(String email);

    String generateConfirmationCode();

    UserTokenData getUserFromToken(String token);
}
