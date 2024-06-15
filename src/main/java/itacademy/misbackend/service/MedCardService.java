package itacademy.misbackend.service;

import itacademy.misbackend.dto.MedCardDto;

import java.util.List;

public interface MedCardService {

    MedCardDto getById (Long id);

    List<MedCardDto> getAll();

}
