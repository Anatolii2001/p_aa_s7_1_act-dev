package app.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This class represent an Entity Flight, includes fields:
 * destination from/to;
 * localDateTime departure/arrival;
 * aircraft - assigned aircraft;
 * flight status - ACCORDING_TO_PLAN, DELAY, CANCELED;
 *
 * @author - Babkin Artyom
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Destination from;

    @ManyToOne(fetch = FetchType.LAZY)
    private Destination to;

    @Column(name="departureDateTime")
    private LocalDateTime departureDateTime;

    @Column(name="arrivalDateTime")
    private LocalDateTime arrivalDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Aircraft aircraft;

    @Column(name="flightStatus")
    private FlightStatus flightStatus;

}
