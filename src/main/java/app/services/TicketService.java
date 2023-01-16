package app.services;

import app.entities.Ticket;

import java.util.List;

public interface TicketService {
    void saveTicket(Ticket ticket);

    List<Ticket> getAllTickets();

    Ticket getTicket(Long id);

    void deleteTicket(Long id);

    void editTicket(Ticket ticket);

    void deleteAllTickets();

    List<Ticket> findAllTicketsByFlightId(Long id);

    //проданные билеты
    List<Ticket> findTicketsByPassengerNotNull();

    boolean isPassengerRegistered(Ticket ticket);

    boolean isFlightCompleted(Ticket ticket);
}
