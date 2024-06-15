package itacademy.misbackend.repo;

import itacademy.misbackend.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTypeRepo extends JpaRepository<ServiceType, Long> {
    ServiceType findByDeletedAtIsNullAndId(Long id);
    List<ServiceType> findAllByDeletedAtIsNull();
}
