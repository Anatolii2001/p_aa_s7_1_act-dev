package app.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Flight Remind Every Hour Event, stores event information
 *
 * @author Alexander Plekhov
 */

@Getter
@AllArgsConstructor
public class RemindAboutFlightEvent {
    private final LocalDateTime currentDateTime;
}
