package itacademy.misbackend.mapper.impl;

import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.entity.Patient;
import itacademy.misbackend.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PatientMapperImpl implements PatientMapper {
    @Override
    public PatientDto toDto(Patient patient) {
        PatientDto dto = PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .patronymic(patient.getPatronymic())
                .dateOfBirth(patient.getDateOfBirth())
                .sex(patient.getSex())
                .passport(patient.getPassport())
                .taxId(patient.getTaxId())
                .address(patient.getAddress())
                .placeOfWork(patient.getPlaceOfWork())
                .phoneNumber(patient.getPhoneNumber())
                .build();
        if (patient.getUser() != null) {
            dto.setUserId(patient.getUser().getId());
        }
     //   if (patient.getAppointments() != null) {
     //       dto.setAppointments(new AppointmentMapperImpl().toDtoList(patient.getAppointments()));
     //   }
        return dto;
    }

    @Override
    public Patient toEntity(PatientDto patientDto) {
        Patient patient = Patient.builder()
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .patronymic(patientDto.getPatronymic())
                .dateOfBirth(patientDto.getDateOfBirth())
                .sex(patientDto.getSex())
                .taxId(patientDto.getTaxId())
                .placeOfWork(patientDto.getPlaceOfWork())
                .phoneNumber(patientDto.getPhoneNumber())
                .passport(patientDto.getPassport())
                .address(patientDto.getAddress())
                .build();
        return patient;
    }

    @Override
    public List<PatientDto> toDtoList(List<Patient> patient) {
        var dtos = new ArrayList<PatientDto>();
        for (Patient p : patient) {
            dtos.add(toDto(p));
        }
        return dtos;
    }
}
