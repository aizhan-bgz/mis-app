package itacademy.misbackend.repo;

import itacademy.misbackend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    Doctor findByDeletedAtIsNullAndDeletedByIsNullAndId(Long id);
    List<Doctor> findAllByDeletedAtIsNullAndDeletedByIsNull();
}
