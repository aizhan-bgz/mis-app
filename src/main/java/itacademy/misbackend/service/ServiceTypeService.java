package itacademy.misbackend.service;

import itacademy.misbackend.dto.ServiceTypeDto;

import java.util.List;

public interface ServiceTypeService {
    ServiceTypeDto create(ServiceTypeDto dto);
    ServiceTypeDto getById(Long id);
    List<ServiceTypeDto> getAll();
    ServiceTypeDto update(Long id, ServiceTypeDto updateDto);
    String delete(Long id);
}
