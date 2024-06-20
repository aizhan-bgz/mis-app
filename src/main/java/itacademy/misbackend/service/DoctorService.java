package itacademy.misbackend.service;

import itacademy.misbackend.dto.DoctorDto;
import itacademy.misbackend.entity.Doctor;
import itacademy.misbackend.entity.User;

import java.util.List;

public interface DoctorService {
    DoctorDto save(DoctorDto dto);
    DoctorDto getById(Long id);
    List<DoctorDto> getAll();
    DoctorDto update(Long id, DoctorDto updateDto);
    String delete(Long id);
    Doctor findByUser(User user);
}
