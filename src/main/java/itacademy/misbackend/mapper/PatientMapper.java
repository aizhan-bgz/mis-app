package itacademy.misbackend.mapper;

import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.entity.Patient;

import java.util.List;

public interface PatientMapper {
    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto patientDto);

    List<PatientDto> toDtoList(List<Patient> patient);
}
