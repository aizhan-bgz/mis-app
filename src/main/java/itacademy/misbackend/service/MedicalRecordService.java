package itacademy.misbackend.service;

import itacademy.misbackend.dto.MedicalRecordDto;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecordDto create (MedicalRecordDto recordDto);

    MedicalRecordDto getById (Long id);

    List<MedicalRecordDto> getAll();

    MedicalRecordDto update (Long id, MedicalRecordDto dto);

    String delete (Long id);


}
