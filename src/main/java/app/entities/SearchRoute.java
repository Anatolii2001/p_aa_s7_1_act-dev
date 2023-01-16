package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * SearchRoute class briefly describes route
 * @author Komarov Rostislav
 */
@Data
@AllArgsConstructor
public class SearchRoute {

    /**
     * link to current flight
     */
    @NonNull
    private Flight flight;

    /**
     * {@link app.entities.Destination} class show information of location where departing from
     */
    @NonNull
    private Destination from;

    /**
     * {@link app.entities.Destination} class show information of location arriving to
     */
    @NonNull
    private Destination to;

    /**
     * Date of departure
     */
    private LocalDate departureDate;

    /**
     * minimal price of available ticket
     */
    @NonNull
    @Min(value = 1)
    private Integer minimalAvailablePrice;
}
