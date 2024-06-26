package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.AppointmentDto;
import itacademy.misbackend.entity.Appointment;
import itacademy.misbackend.entity.Doctor;
import itacademy.misbackend.entity.Patient;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.mapper.AppointmentMapper;
import itacademy.misbackend.repo.AppointmentRepo;
import itacademy.misbackend.repo.DoctorRepo;
import itacademy.misbackend.repo.PatientRepo;
import itacademy.misbackend.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;

    @Transactional
    @Override
    public AppointmentDto create(AppointmentDto appointmentDto) {
        log.info("СТАРТ: AppointmentServiceImpl - create() {}", appointmentDto);
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);

        Doctor doctor = doctorRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(appointmentDto.getDoctorId());
        if (doctor == null) {
            throw new NotFoundException("Врач с id " + appointmentDto.getDoctorId() + " не найден");
        }
        appointment.setDoctor(doctor);

        Patient patient = patientRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(appointmentDto.getPatientId());
        if (patient == null) {
            throw new NotFoundException("Пациент с id " + appointmentDto.getPatientId() + " не найден");
        }
        appointment.setPatient(patient);
        appointment.setStatus("Запланирован");

        appointment = appointmentRepo.save(appointment);
        log.info("КОНЕЦ: AppointmentServiceImpl - create() {}", appointmentMapper.toDto(appointment));
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public AppointmentDto getById(Long id) {
        log.info("СТАРТ: AppointmentServiceImpl - getById({})", id);
        Appointment appointment = appointmentRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (appointment == null) {
            throw new NotFoundException("Запись приема с id " + id + " не найдена!");
        }
        log.info("КОНЕЦ: AppointmentServiceImpl - getById(). Прием - {} ", appointment);
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public List<AppointmentDto> getAll() {
        log.info("СТАРТ: AppointmentServiceImpl - getAll()");
        var list = (appointmentRepo.findAllByDeletedAtIsNullAndDeletedByIsNull());
        if (list == null) {
            throw new NotFoundException("Список приемов пуст!");
        }
        log.info("КОНЕЦ: AppointmentServiceImpl - getAll()");
        return appointmentMapper.toDtoList(list);
    }

    @Override
    public AppointmentDto update(Long id, AppointmentDto appointmentDto) {
        log.info("СТАРТ: AppointmentServiceImpl - update(). Прием с id {}", id);
        Appointment appointment = appointmentRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);

        if (appointment == null) {
            throw new NotFoundException("Запись приема с id " + id + " не найдена!");
        }
        if (appointmentDto.getReason() != null) {
            appointment.setReason(appointmentDto.getReason());
        }
        if (appointmentDto.getAppointmentDate() != null) {
            appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        }
        appointment = appointmentRepo.save(appointment);
        log.info("КОНЕЦ: AppointmentServiceImpl - update(). Запись приема обновлена - {}", appointment);
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public String delete(Long id) {
        log.info("СТАРТ: AppointmentServiceImpl - delete(). Прием с id {}", id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Appointment appointment = appointmentRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (appointment == null) {
            throw new NotFoundException("Запись приема с id " + id + " не найдена!");
        }
        appointment.setDeletedAt(LocalDateTime.now());
        appointment.setDeletedBy(authentication.getName());
        appointmentRepo.save(appointment);
        log.info("КОНЕЦ: DoctorServiceImpl - delete(). Запись приема (id {}) удалена", id);
        return "Запись приема с id " + id + " удалена";
    }
}
