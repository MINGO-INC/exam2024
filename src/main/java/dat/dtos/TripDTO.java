package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.entities.Category;
import dat.entities.Trip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)

public class TripDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startposition;
    private String name;
    private double price;
    private Category category;
    private GuideDTO guide;


    public TripDTO(LocalDateTime startTime, LocalDateTime endTime, String startposition, String name, double price, Category category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startposition = startposition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startposition = trip.getStartposition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();

        // Initialize guide if it exists
        if (trip.getGuide() != null) {
            this.guide = new GuideDTO(trip.getGuide()); // Convert Guide to GuideDTO
        }

    }
}
