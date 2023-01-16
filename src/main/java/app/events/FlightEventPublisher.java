package app.events;

import app.entities.Flight;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Flight Event Publisher, publish an event for Flight Event Listener
 * Accepts the object Flight changed after the update in FlightService update()
 *
 * @author Alexander Plekhov
 */

@Component
@AllArgsConstructor
public class FlightEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void startFlightStatusChangeEvent(Flight flight) {
        applicationEventPublisher.publishEvent(new FlightStatusChangeEvent(flight));
    }

    public void startFlightAircraftReplacementEvent(Flight flight) {
        applicationEventPublisher.publishEvent(new FlightAircraftReplacementEvent(flight));
    }

    public void startRemindAboutFlightEvent(LocalDateTime currentDateTime) {
        applicationEventPublisher.publishEvent(new RemindAboutFlightEvent(currentDateTime));
    }
}
