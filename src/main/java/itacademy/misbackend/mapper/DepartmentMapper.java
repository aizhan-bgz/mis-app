package itacademy.misbackend.mapper;

import itacademy.misbackend.dto.DepartmentDto;
import itacademy.misbackend.entity.Department;

import java.util.List;

public interface DepartmentMapper {
    DepartmentDto toDto(Department department);

    Department toEntity(DepartmentDto departmentDto);

    List<DepartmentDto> toDtoList(List<Department> department);
}
