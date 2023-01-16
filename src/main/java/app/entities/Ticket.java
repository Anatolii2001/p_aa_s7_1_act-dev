package app.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Class Ticket with properties <b>passenger</b>,
 * <b>flight</b>, <b>seat</b>, <b>subcategory</b>,
 * <b>bookingNumber</b>
 *
 * @author Tamara Ustyan
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Passenger passenger;

    @ManyToOne
    private Flight flight;

    @OneToOne
    private Seat seat;

    private Subcategory subcategory;

    @NotNull
    private String bookingNumber;



    public enum Subcategory {
        ECONOMY_CLASS("Эконом"),
        COMFORT_CLASS("Комфорт"),
        BUSINESS_CLASS("Бизнес")
        ;

        private String translation;

        Subcategory(String translation) {
            this.translation = translation;
        }
    }
}
