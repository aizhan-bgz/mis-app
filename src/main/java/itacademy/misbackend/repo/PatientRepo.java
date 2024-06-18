package itacademy.misbackend.repo;

import itacademy.misbackend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
    Patient findByDeletedAtIsNullAndDeletedByIsNullAndId(Long id);
    List<Patient> findAllByDeletedAtIsNullAndDeletedByIsNull();
    Boolean existsByPassport(String passport);
    Boolean existsByTaxId(String taxId);

}
