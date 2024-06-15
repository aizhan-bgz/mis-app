package itacademy.misbackend.entity;

import itacademy.misbackend.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq_generator")
    @SequenceGenerator(name = "patient_seq_generator", sequenceName = "patient_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(nullable = false)
    private LocalDate dateOfBirth;
    private String passport;

    @Column(unique = true, nullable = false)
    private String taxId;
    private String address;
    private String placeOfWork;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToOne
    private User user;

//    @OneToMany(mappedBy = "patient")
  //  private List<Appointment> appointments;

    private LocalDateTime deletedAt;
    private String deletedBy;
    
}
