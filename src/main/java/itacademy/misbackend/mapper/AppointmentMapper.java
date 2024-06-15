package itacademy.misbackend.mapper;

import itacademy.misbackend.dto.AppointmentDto;
import itacademy.misbackend.entity.Appointment;

import java.util.List;

public interface AppointmentMapper {
    AppointmentDto toDto(Appointment appointment);

    Appointment toEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toDtoList(List<Appointment> appointment);
}
