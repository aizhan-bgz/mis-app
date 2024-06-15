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
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "med_record_seq_generator")
    @SequenceGenerator(name = "med_record_seq_generator", sequenceName = "med_record_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "med_card_id")
    private MedCard medCard;

    private String diagnosis;
    private String prescription;
    private String notes;
    private String recommendation;

    private String createdBy;
    private LocalDateTime createdAt;

    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    private String deletedBy;
    private LocalDateTime deletedAt;

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "id=" + id +
                ", appointment=" + appointment +
                ", medCardID=" + medCard.getId() +
                ", diagnosis=" + diagnosis +
                ", prescription=" + prescription +
                ", notes='" + notes + '\'' +
                ", recommendation='" + recommendation + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", deletedBy='" + deletedBy + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
