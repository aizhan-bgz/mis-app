package itacademy.misbackend.service;

import itacademy.misbackend.dto.UpdatedUser;
import itacademy.misbackend.dto.UserDoctorRequest;
import itacademy.misbackend.dto.UserDto;
import itacademy.misbackend.dto.UserPatientRequest;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);
    UserDoctorRequest createDoctor(UserDoctorRequest userDoctorRequest);
    UserPatientRequest createPatient(UserPatientRequest userPatientRequest);
    UserDto getById(Long id);
    List<UserDto> getAll();
    void addRole(Long id, String role);
    UserDto update(Long id, UpdatedUser updatedUser);
    String delete(Long id);

    Long getUserIdByEmail(String email);

    String generateConfirmationCode();
}
