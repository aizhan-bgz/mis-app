package itacademy.misbackend.service;

import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.entity.Patient;
import itacademy.misbackend.entity.User;

import java.util.List;

public interface PatientService {
    PatientDto create(PatientDto patientDto);
    PatientDto getById(Long id);
    List<PatientDto> getAll();
    PatientDto update(Long id, PatientDto patientDto);
    String delete(Long id);

    Patient findByUser(User user);
}
