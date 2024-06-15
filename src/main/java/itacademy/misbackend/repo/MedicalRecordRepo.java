package itacademy.misbackend.repo;

import itacademy.misbackend.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicalRecordRepo extends JpaRepository <MedicalRecord, Long> {

    MedicalRecord findByDeletedAtIsNullAndId (Long id);
    List<MedicalRecord> findAllByDeletedAtIsNull();

}
