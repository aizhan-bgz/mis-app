package itacademy.misbackend.service;

import itacademy.misbackend.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto create(DepartmentDto dto);
    DepartmentDto getById(Long id);
    List<DepartmentDto> getAll();
    DepartmentDto update(Long id, DepartmentDto updateDto);
    String delete(Long id);
}
