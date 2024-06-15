package itacademy.misbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceTypeDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Long departmentId;

}
