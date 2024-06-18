package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.MedCardDto;
import itacademy.misbackend.entity.MedCard;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.mapper.MedicalRecordMapper;
import itacademy.misbackend.repo.MedCardRepo;
import itacademy.misbackend.service.MedCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedCardServiceImpl implements MedCardService {
    private final MedCardRepo repo;
    private final MedicalRecordMapper recordMapper;

    @Override
    public MedCardDto getById(Long id) {
        log.info("СТАРТ: MedCardServiceImpl - getById(). Мед карта с id {}", id);
        MedCard medCard = repo.findByDeletedAtIsNullAndId(id);
        if (medCard == null) {
            throw new NotFoundException("Мед карта с id " + id + " не найдена!");
        }
         MedCardDto medCardDto = MedCardDto.builder()
                 .id(medCard.getId())
                 .patient(medCard.getPatient())
                 .medicalRecords(recordMapper.toDtoList(medCard.getMedicalRecords()))
                 .build();
        log.info("КОНЕЦ: MedCardServiceImpl - getById(). Мед карта - {} ", medCardDto);
        return medCardDto;
    }

    @Override
    public List<MedCardDto> getAll() {
        log.info("СТАРТ: MedCardServiceImpl - getAll()");
        List<MedCard> medCardList = repo.findAllByDeletedAtIsNull();
        if (medCardList.isEmpty()) {
            throw new NotFoundException("Мед карт нет!");
        }
        List<MedCardDto> medCardDtoList = new ArrayList<>();
        for (MedCard medCard : medCardList) {
            MedCardDto medCardDto = MedCardDto.builder()
                    .id(medCard.getId())
                    .patient(medCard.getPatient())
                    .medicalRecords(recordMapper.toDtoList(medCard.getMedicalRecords()))
                    .build();
            medCardDtoList.add(medCardDto);
        }
        log.info("КОНЕЦ: MedCardServiceImpl - getAll()");
        return medCardDtoList;
    }

}
