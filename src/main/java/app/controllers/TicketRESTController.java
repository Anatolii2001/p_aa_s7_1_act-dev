package app.controllers;

import app.entities.Ticket;
import app.services.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRESTController {

    static final Logger logger = LogManager.getLogger(TicketRESTController.class);
    private final TicketService ticketService;

    @Autowired
    public TicketRESTController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createNewTicket(@RequestBody Ticket ticket) {
        ticketService.saveTicket(ticket);
        logger.info("Ticket created");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicket(id);
        if (ticket == null) {
            logger.warn("Ticket not found: " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }
        logger.info("Show ticket: " + id);
        return ticket != null
                ? new ResponseEntity<>(ticket, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<Ticket>> getListTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        logger.info("Show all tickets");
        return tickets != null
                ? new ResponseEntity<>(tickets, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicket(id);
        if (ticket == null) {
            logger.warn("Ticket not found: " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }
        ticketService.deleteTicket(id);
        logger.info("Ticket deleted: " + id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAllTickets() {
        logger.info("Delete all tickets");
        ticketService.deleteAllTickets();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> editTicket(@RequestBody Ticket ticket) {
        if (ticketService.getTicket(ticket.getId()) == null) {
            logger.warn("Ticket not found: " + ticket.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }
        logger.info("Ticket edited: " + ticket.getId());
        ticketService.editTicket(ticket);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("flight/{id}")
    public ResponseEntity<List<Ticket>> getAllTicketsByFlightId(@PathVariable Long id) {
        List<Ticket> tickets = ticketService.findAllTicketsByFlightId(id);
        logger.info("Get all tickets " + getAllTicketsByFlightId(id));
        return tickets != null
                ? new ResponseEntity<>(tickets, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
