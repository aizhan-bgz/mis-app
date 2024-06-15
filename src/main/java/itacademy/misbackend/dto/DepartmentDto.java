package itacademy.misbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private Long id;
    private String name;
    private String description;
    private List<DoctorDto> doctors;
//    private List<ServiceTypeDto> services;

    public DepartmentDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


}
