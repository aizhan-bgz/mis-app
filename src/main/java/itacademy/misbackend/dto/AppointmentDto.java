package itacademy.misbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {
    private Long id;
    private String reason;
    private String status;
    private LocalDateTime appointmentDate;
    private Long doctorId;
    private Long patientId;
    private Long medicalRecordId;

}
