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
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq_generator")
    @SequenceGenerator(name = "department_seq_generator", sequenceName = "department_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors;

//    @OneToMany(mappedBy = "department")
  //  private List<ServiceType> services;

    private LocalDateTime deletedAt;
    private String deletedBy;

}
