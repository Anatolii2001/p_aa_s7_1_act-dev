package app.events;

import app.entities.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Flight Aircraft Replacement Event, stores event information
 *
 * @author Alexander Plekhov
 */

@Getter
@AllArgsConstructor
public class FlightAircraftReplacementEvent {
    private final Flight flight;
}
