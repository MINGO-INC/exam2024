package dat.entities;

import dat.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author laith kaseb
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guide")
/*
2.3 Implement a Guide entity class with the following properties:
 firstname, lastname, email, phone, yearsOfExperience, and a OneToMany relationship to trips.
 */
public class Guide  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExperience;

    // OneToMany relationship to trips
    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips;

    public Guide(String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }
    public Guide(GuideDTO guideDTO) {
        this.firstName = guideDTO.getFirstName();
        this.lastName = guideDTO.getLastName();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();

    }

}
