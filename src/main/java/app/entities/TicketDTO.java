package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    boolean isPassengerRegistered;
    boolean isFlightCompleted;
}
