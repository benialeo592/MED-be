package med.be.peoplemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "patients",
        indexes = { @Index( name = "idx_fiscal_code", columnList = "fiscal_code", unique = true),
                    @Index( name = "idx_email", columnList = "email", unique = true)
})
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "fiscal_code", unique = true)
    private String fiscalCode;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email", unique = true)
    private String email;


}
