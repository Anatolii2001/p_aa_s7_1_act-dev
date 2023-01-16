package app.events;

import app.entities.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Flight Change Status Event, stores event information
 *
 * @author Alexander Plekhov
 */

@Getter
@AllArgsConstructor
public class FlightStatusChangeEvent {
    private final Flight flight;
}
