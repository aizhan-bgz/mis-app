package itacademy.misbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDto {
    private Long id;

    private Long appointmentId;
    private Long medCardId;

    private String diagnosis;
    private String prescription;
    private String notes;
    private String recommendation;

    private String createdBy;
    private LocalDateTime createdAt;

    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;
}
