package itacademy.misbackend.mapper;

import itacademy.misbackend.dto.MedicalRecordDto;
import itacademy.misbackend.entity.MedicalRecord;

import java.util.List;

public interface MedicalRecordMapper {
    MedicalRecordDto toDto(MedicalRecord record);

    MedicalRecord toEntity(MedicalRecordDto recordDto);

    List<MedicalRecordDto> toDtoList(List<MedicalRecord> recordList);
}

