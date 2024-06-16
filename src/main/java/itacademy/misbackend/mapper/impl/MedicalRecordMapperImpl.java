package itacademy.misbackend.mapper.impl;

import itacademy.misbackend.dto.MedicalRecordDto;
import itacademy.misbackend.entity.MedicalRecord;
import itacademy.misbackend.mapper.MedicalRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class MedicalRecordMapperImpl implements MedicalRecordMapper {

    @Override
    public MedicalRecordDto toDto(MedicalRecord record) {
        return MedicalRecordDto.builder()
                .id(record.getId())
                .appointmentId(record.getAppointment().getId())
                .medCardId(record.getMedCard().getId())
                .diagnosis(record.getDiagnosis())
                .prescription(record.getPrescription())
                .notes(record.getNotes())
                .recommendation(record.getRecommendation())
                .createdAt(record.getCreatedAt())
                .createdBy(record.getCreatedBy())
                .lastUpdatedAt(record.getLastUpdatedAt())
                .lastUpdatedBy(record.getLastUpdatedBy())
               .build();
    }

    @Override
    public MedicalRecord toEntity(MedicalRecordDto recordDto) {
        return MedicalRecord.builder()
                .diagnosis(recordDto.getDiagnosis())
                .prescription(recordDto.getPrescription())
                .notes(recordDto.getNotes())
                .recommendation(recordDto.getRecommendation())
                .build();
    }

    @Override
    public List<MedicalRecordDto> toDtoList(List<MedicalRecord> recordList) {
        var dtoList = new ArrayList<MedicalRecordDto>();
        for (MedicalRecord m : recordList) {
            dtoList.add(toDto(m));
        }
        return dtoList;
    }
}



