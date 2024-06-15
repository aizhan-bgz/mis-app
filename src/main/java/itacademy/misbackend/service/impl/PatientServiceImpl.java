package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.PatientDto;
import itacademy.misbackend.entity.Patient;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.mapper.PatientMapper;
import itacademy.misbackend.repo.PatientRepo;
import itacademy.misbackend.repo.UserRepo;
import itacademy.misbackend.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
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
   // private final MedCardRepo medCardRepo;

    @Override
    public PatientDto create(PatientDto patientDto) {
        log.info("СТАРТ: PatientServiceImpl - create() {}", patientDto);
        Patient patient = patientMapper.toEntity(patientDto);
        patient.setUser(userRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(patientDto.getUserId()));
        patient = patientRepo.save(patient);

   //     MedCard medCard = new MedCard();
    //    medCard.setPatient(patient); // Связываем медицинскую карту с пациентом
    //    medCardRepo.save(medCard);   // Сохраняем медицинскую карту

     //   patient.setMedCard(medCard);
        patientRepo.save(patient);
        log.info("КОНЕЦ: PatientServiceImpl - create {} ", patientMapper.toDto(patient));
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDto getById(Long id) {
        log.info("СТАРТ: PatientServiceImpl - getById({})", id);
        Patient patient = patientRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (patient == null) {
            log.error("Пациент с id " + id + " не найден!");
            throw new NotFoundException("Пациент не найден!");
        }
        log.info("КОНЕЦ: PatientServiceImpl - getById(). Пациент - {} ", patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public List<PatientDto> getAll() {
        log.info("СТАРТ: PatientServiceImpl - getAll()");
        var patientList = patientRepo.findAllByDeletedAtIsNullAndDeletedByIsNull();
        if (patientList.isEmpty()) {
            log.error("Пациентов нет!");
            throw new NotFoundException("Пациентов нет!");
        }
        log.info("КОНЕЦ: PatientServiceImpl - getAll()");
        return patientMapper.toDtoList(patientList);
    }

    @Override
    public PatientDto update(Long id, PatientDto patientDto) {
        log.info("СТАРТ: PatientServiceImpl - update(). Пациент с id {}", id);
        Patient patient = patientRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
        if (patient != null){
            if (patientDto.getDateOfBirth() != null){
                patient.setDateOfBirth(patientDto.getDateOfBirth());
            }
            if (patientDto.getSex() != null){
                patient.setSex(patientDto.getSex());
            }
            if (patientDto.getPassport() != null){
                patient.setPassport(patientDto.getPassport());
            }
            if (patientDto.getTaxId() != null){
                patient.setTaxId(patientDto.getTaxId());
            }
            if (patientDto.getAddress() != null){
                patient.setAddress(patientDto.getAddress());
            }
            if (patientDto.getPlaceOfWork() != null){
                patient.setPlaceOfWork(patientDto.getPlaceOfWork());
            }
            patient = patientRepo.save(patient);
            log.info("КОНЕЦ: PatientServiceImpl - update(). Обновленная запись - {}", patient);
            return patientMapper.toDto(patient);
        }
        return null;
    }

    @Override
    public String delete(Long id) {
        log.info("СТАРТ: PatientServiceImpl - delete(). Пациент с id {}", id);
        Patient patient = patientRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(id);
      //  MedCard medCard = medCardRepo.findByDeletedAtIsNullAndId(id);
      //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (patient == null) {
            log.error("Пациент с id " + id + " не найден!");
            throw new NotFoundException("Пациент с id " + id + " не найден");
        }
            patient.setDeletedAt(LocalDateTime.now());
         //   patient.setDeletedBy(authentication.getName());
            patientRepo.save(patient);

         ///   medCard.setDeletedAt(LocalDateTime.now());
         //   medCard.setDeletedBy(authentication.getName());
         //   medCardRepo.save(medCard);

            log.info("КОНЕЦ: PatientServiceImpl - delete(). Пациент (id {}) удален", id);
            return "Пациент с id " + id + " удален";
    }
}
