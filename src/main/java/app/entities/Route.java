package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity class for Route
 * @author Olga Maslova
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Route")
public class Route {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "destination_from_id", referencedColumnName = "id")
    private Destination from;

    @OneToOne
    @JoinColumn(name = "destination_to_id", referencedColumnName = "id")
    private Destination to;

    @Column
    LocalDate departureDate;

    @Column
    LocalDate arrivalDate;

    @Column
    Integer numberOfSeats;

    @Enumerated(EnumType.STRING)
    CategoryType category;

}
