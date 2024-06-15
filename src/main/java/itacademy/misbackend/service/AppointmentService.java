package itacademy.misbackend.service;

import itacademy.misbackend.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {
    AppointmentDto create(AppointmentDto appointmentDto);
    AppointmentDto getById(Long id);
    List<AppointmentDto> getAll();
    AppointmentDto update(Long id, AppointmentDto appointmentDto);
    String delete(Long id);

}
