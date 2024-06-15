package itacademy.misbackend.mapper.impl;

import itacademy.misbackend.dto.AppointmentDto;
import itacademy.misbackend.entity.Appointment;
import itacademy.misbackend.mapper.AppointmentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentMapperImpl implements AppointmentMapper {
    @Override
    public AppointmentDto toDto(Appointment appointment) {
        AppointmentDto dto = AppointmentDto.builder()
                .id(appointment.getId())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .appointmentDate(appointment.getAppointmentDate())
                .build();
        if (appointment.getMedicalRecord() != null) {
            dto.setMedicalRecordId(appointment.getMedicalRecord().getId());
        }
        return dto;
    }

    @Override
    public Appointment toEntity(AppointmentDto appointmentDto) {
        return Appointment.builder()
                .reason(appointmentDto.getReason())
                .status(appointmentDto.getStatus())
                .appointmentDate(appointmentDto.getAppointmentDate())
                .build();
    }

    @Override
    public List<AppointmentDto> toDtoList(List<Appointment> appointment) {
        var dtos = new ArrayList<AppointmentDto>();
        for (Appointment a : appointment) {
            dtos.add(toDto(a));
        }
        return dtos;
    }
}
