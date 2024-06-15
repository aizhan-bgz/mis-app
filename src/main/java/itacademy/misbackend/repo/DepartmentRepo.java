package itacademy.misbackend.repo;

import itacademy.misbackend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Department findByDeletedAtIsNullAndDeletedByIsNullAndId(Long id);
    List<Department> findAllByDeletedAtIsNullAndDeletedByIsNull();
}
