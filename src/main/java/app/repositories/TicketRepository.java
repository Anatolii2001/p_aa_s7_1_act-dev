package app.repositories;

import app.entities.Flight;
import app.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllTicketByFlight(Flight flight);

    List<Ticket> findTicketsByPassengerNotNull();



}
