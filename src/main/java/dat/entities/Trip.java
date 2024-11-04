package dat.entities;

/*
2.2 Implement a Trip entity class with the following properties:
starttime, endtime, startposition, name, price, id,
category. Use an enum for the category of the trip.


 */


import dat.dtos.TripDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.Position;
import javax.xml.namespace.QName;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startposition;
    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    private Guide guide;

    public Trip(LocalDateTime startTime, LocalDateTime endTime, String startposition, String name, double price, Category category, Guide guide) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startposition = startposition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }
    public Trip(TripDTO tripDTO) {
        this.startTime = tripDTO.getStartTime();
        this.endTime = tripDTO.getEndTime();
        this.startposition = tripDTO.getStartposition();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();

    }

}
