package itacademy.misbackend.mapper;

import itacademy.misbackend.dto.DoctorDto;
import itacademy.misbackend.entity.Doctor;

import java.util.List;

public interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);

    Doctor toEntity(DoctorDto doctorDto);

    List<DoctorDto> toDtoList(List<Doctor> doctor);
}
