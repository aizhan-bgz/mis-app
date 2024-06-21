package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.ErrorMessage;
import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.entity.MedCard;
import itacademy.misbackend.entity.Patient;
import itacademy.misbackend.entity.User;
import itacademy.misbackend.exception.DuplicateValueException;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.mapper.PatientMapper;
import itacademy.misbackend.repo.MedCardRepo;
import itacademy.misbackend.repo.PatientRepo;
import itacademy.misbackend.repo.UserRepo;
import itacademy.misbackend.service.PatientService;
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
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;
    private final UserRepo userRepo;
    private final MedCardRepo medCardRepo;

    @Override
    public PatientDto create(PatientDto patientDto) {
        log.info("СТАРТ: PatientServiceImpl - create() {}", patientDto);
        Patient patient = patientMapper.toEntity(patientDto);
        patient.setUser(userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(patientDto.getUserId()));
        patient = patientRepo.save(patient);

        MedCard medCard = new MedCard();
        medCard.setId(patient.getId());
        medCard.setPatient(patient.getLastName() + " " +
                            patient.getFirstName()+ " " +
                            patient.getPatronymic());
        medCardRepo.save(medCard);
        log.info("КОНЕЦ: PatientServiceImpl - create {} ", patientMapper.toDto(patient));
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDto getById(Long id) {
        log.info("СТАРТ: PatientServiceImpl - getById({})", id);
        Patient patient = patientRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (patient == null) {
            throw new NotFoundException("Пациент с id " + id + " не найден");
        }
        log.info("КОНЕЦ: PatientServiceImpl - getById(). Пациент - {} ", patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public List<PatientDto> getAll() {
        log.info("СТАРТ: PatientServiceImpl - getAll()");
        var patientList = patientRepo.findAllByDeletedAtIsNullAndDeletedByIsNull();
        if (patientList.isEmpty()) {
            throw new NotFoundException("Список пациентов пуст");
        }
        log.info("КОНЕЦ: PatientServiceImpl - getAll()");
        return patientMapper.toDtoList(patientList);
    }

    @Override
    public PatientDto update(Long id, PatientDto patientDto) {
        log.info("СТАРТ: PatientServiceImpl - update(). Пациент с id {}", id);
        ErrorMessage errorMessage = new ErrorMessage();
        Patient patient = patientRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (patient == null) {
            throw new NotFoundException("Пациент с id " + id + " не найден");
        }
        if (patientDto.getDateOfBirth() != null){
            patient.setDateOfBirth(patientDto.getDateOfBirth());
        }
        if (patientDto.getSex() != null){
            patient.setSex(patientDto.getSex());
        }
        if (patientDto.getPassport() != null) {
            if (!patientRepo.existsByPassport(patientDto.getPassport())) {
                patient.setPassport(patientDto.getPassport());
            } else {
                errorMessage.addError("passport", "Пациент с указанным паспортом уже существует");
            }
        } else {
            errorMessage.addError("passport", "Паспорт не может быть пустым");
        }
        if (patientDto.getTaxId() != null) {
            if (!patientRepo.existsByTaxId(patientDto.getTaxId())) {
                patient.setTaxId(patientDto.getTaxId());
            } else {
                errorMessage.addError("taxId", "Пациент с указанным ИНН (" + patientDto.getTaxId() + ") уже существует");
            }
        } else {
            errorMessage.addError("taxId", "ИНН не может быть пустым");
        }
        if (patientDto.getAddress() != null){
            patient.setAddress(patientDto.getAddress());
        }
        if (patientDto.getPlaceOfWork() != null){
            patient.setPlaceOfWork(patientDto.getPlaceOfWork());
        }
        if (!errorMessage.getErrors().isEmpty()) {
            throw new DuplicateValueException(errorMessage);
        }
        patient = patientRepo.save(patient);
        log.info("КОНЕЦ: PatientServiceImpl - update(). Обновленная запись - {}", patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public String delete(Long id) {
        log.info("СТАРТ: PatientServiceImpl - delete(). Пациент с id {}", id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = patientRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (patient == null) {
            throw new NotFoundException("Пациент с id " + id + " не найден");
        }
        MedCard medCard = medCardRepo.findByDeletedAtIsNullAndId(id);

        patient.setDeletedAt(LocalDateTime.now());
        patient.setDeletedBy(authentication.getName());
        patientRepo.save(patient);

        medCard.setDeletedAt(LocalDateTime.now());
        medCard.setDeletedBy(authentication.getName());
        medCardRepo.save(medCard);

        log.info("КОНЕЦ: PatientServiceImpl - delete(). Пациент (id {}) удален", id);
        return "Пациент с id " + id + " удален";
    }

    @Override
    public Patient findByUser(User user) {
        return patientRepo.findByUser(user);
    }
}
