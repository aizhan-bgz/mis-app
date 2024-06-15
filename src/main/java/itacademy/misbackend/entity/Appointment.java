package itacademy.misbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq_generator")
    @SequenceGenerator(name = "appointment_seq_generator", sequenceName = "appointment_seq", allocationSize = 1)
    private Long id;
    private String reason;
    private String status;
    private LocalDateTime appointmentDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

    private LocalDateTime deletedAt;
    private String deletedBy;

    @Override
    public String toString() {
        return "Appointment [id=" + id
                + ", doctorId=" + doctor.getId()
                + ", patientId=" + patient.getId()
                + ", medicalRecordId=" + medicalRecord.getId()
                + "]";
    }
}
