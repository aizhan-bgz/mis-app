package itacademy.misbackend.repo;

import itacademy.misbackend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    Appointment findByDeletedAtIsNullAndDeletedByIsNullAndId(Long id);
    List<Appointment> findAllByDeletedAtIsNullAndDeletedByIsNull();
}
