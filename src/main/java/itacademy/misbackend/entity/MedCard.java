package itacademy.misbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedCard {
    @Id
    private Long id;

    private String patient;

    @OneToMany(mappedBy = "medCard", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;

    private String deletedBy;
    private LocalDateTime deletedAt;

    @Override
    public String toString() {
        return "MedCard{" +
                "id=" + id +
                ", patient=" + patient +
                ", medicalRecords=" + medicalRecords +
                ", deletedBy='" + deletedBy + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
