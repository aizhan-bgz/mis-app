package itacademy.misbackend.service.impl;

import itacademy.misbackend.dto.ServiceTypeDto;
import itacademy.misbackend.entity.Department;
import itacademy.misbackend.entity.ServiceType;
import itacademy.misbackend.exception.NotFoundException;
import itacademy.misbackend.repo.DepartmentRepo;
import itacademy.misbackend.repo.ServiceTypeRepo;
import itacademy.misbackend.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceTypeServiceImpl implements ServiceTypeService {
    private final ServiceTypeRepo repo;
    private final DepartmentRepo departmentRepo;

    @Override
    public ServiceTypeDto create(ServiceTypeDto dto) {
        log.info("СТАРТ: ServiceTypeServiceImpl - create() {}", dto);
        ServiceType serviceType = ServiceType.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
        if (departmentRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(dto.getDepartmentId()) == null) {
            throw new NotFoundException("Отделение с id " + dto.getDepartmentId() + " не найдено");
        }
        serviceType.setDepartment(departmentRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(dto.getDepartmentId()));
        repo.save(serviceType);
        dto.setId(serviceType.getId());
        log.info("КОНЕЦ: ServiceTypeServiceImpl - create {} ", serviceType);
        return dto;
    }

    @Override
    public ServiceTypeDto getById(Long id) {
        log.info("СТАРТ: ServiceTypeServiceImpl - getById({})", id);
        ServiceType serviceType = repo.findByDeletedAtIsNullAndId(id);
        if (serviceType == null) {
            log.error("Услуга с id " + id + " не найдена!");
            throw new NotFoundException("Услуга не найдена!");
        }
        log.info("КОНЕЦ: ServiceTypeServiceImpl - getById(). Услуга - {} ", serviceType);
        return ServiceTypeDto.builder()
                .id(serviceType.getId())
                .name(serviceType.getName())
                .description(serviceType.getDescription())
                .price(serviceType.getPrice())
                .departmentId(serviceType.getDepartment().getId() )
                .build();
    }

    @Override
    public List<ServiceTypeDto> getAll() {
        log.info("СТАРТ: ServiceTypeServiceImpl - getAll()");
        List<ServiceType> services = repo.findAllByDeletedAtIsNull();
        if (services.isEmpty()) {
            throw new NotFoundException("Список услуг пуст!");
        }
        var dtoList = new ArrayList<ServiceTypeDto>();
        for (ServiceType serviceType : services) {
            ServiceTypeDto dto = ServiceTypeDto.builder()
                    .id(serviceType.getId())
                    .name(serviceType.getName())
                    .description(serviceType.getDescription())
                    .price(serviceType.getPrice())
                    .departmentId(serviceType.getDepartment().getId() )
                    .build();
            dtoList.add(dto);
        }
        log.info("КОНЕЦ: ServiceTypeServiceImpl - getAll()");
        return dtoList;
    }

    @Override
    public ServiceTypeDto update(Long id, ServiceTypeDto updateDto) {
        log.info("СТАРТ: ServiceTypeServiceImpl - update(). Услуга с id {}", id);
        ServiceType serviceType = repo.findByDeletedAtIsNullAndId(id);

        if (serviceType == null) {
            throw new NotFoundException("Услуга с id " + id + " не найдена!");
        }
        log.info("Услуга найдена. Исходные данные - {}", serviceType);
        if (updateDto.getName() != null) {
            serviceType.setName(updateDto.getName());
        }
        if (updateDto.getDescription() != null) {
            serviceType.setDescription(updateDto.getDescription());
        }
        if (updateDto.getPrice() != null) {
            serviceType.setPrice(updateDto.getPrice());
        }
        if (updateDto.getDepartmentId() != null) {
            Department department = departmentRepo.findByDeletedAtIsNullAndDeletedByIsNullAndId(updateDto.getDepartmentId());
            if (department == null) {
                log.error("Отделение с id " + id + " не найдено!");
                throw new NotFoundException("Отделение с id " + updateDto.getDepartmentId() + " не найдено");
            }
            serviceType.setDepartment(departmentRepo
                    .findByDeletedAtIsNullAndDeletedByIsNullAndId(updateDto.getDepartmentId()));
        }
        serviceType = repo.save(serviceType);
        log.info("КОНЕЦ: ServiceTypeServiceImpl - update(). Обновленная услуга - {}", serviceType);
        return  ServiceTypeDto.builder()
                    .id(serviceType.getId())
                    .name(serviceType.getName())
                    .description(serviceType.getDescription())
                    .price(serviceType.getPrice())
                    .departmentId(serviceType.getDepartment().getId() )
                    .build();
    }

    @Override
    public String delete(Long id) {
        log.info("СТАРТ: ServiceTypeServiceImpl - delete(). Услуга с id {}", id);
        ServiceType serviceType = repo.findByDeletedAtIsNullAndId(id);
        if (serviceType == null) {
            throw new NotFoundException("Услуга с id " + id + " не найдена!");
        }
        serviceType.setDeletedAt(LocalDateTime.now());
        repo.save(serviceType);
        log.info("КОНЕЦ: ServiceTypeServiceImpl - delete(). Услуга {} (id {}) удалена", serviceType.getName(), id);
        return "Услуга удалена";
    }

}
