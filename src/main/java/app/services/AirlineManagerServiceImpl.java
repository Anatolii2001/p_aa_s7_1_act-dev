package app.services;

import app.entities.Aircraft;
import app.entities.Flight;
import app.entities.Ticket;
import app.entities.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Airline Manager can administer:
 * - aircraft (add, delete, patch)
 * - flight (add, delete, patch)
 * - tickets (see the list of tickets
 *  sold and statuses like "passenger not checked in",
 *   "passenger checked in", "flight completed")
 * @author Kalinnikova Marina
 */
@Service
public class AirlineManagerServiceImpl implements AirlineManagerService {

    private AircraftServiceImpl aircraftService;
    private TicketServiceImpl ticketService;
    private FlightServiceImpl flightService;

    @Autowired
    public AirlineManagerServiceImpl(AircraftServiceImpl aircraftService, TicketServiceImpl ticketService, FlightServiceImpl flightService) {
        this.aircraftService = aircraftService;
        this.ticketService = ticketService;
        this.flightService = flightService;
    }

    @Override
    @Transactional
    public void saveAircraft(Aircraft aircraft) {
        aircraftService.save(aircraft);
    }

    @Override
    public Aircraft findAircraftById(Long id) {
        return aircraftService.findById(id).get();
    }

    @Override
    public List<Aircraft> findAllAircraft() {
        return aircraftService.findAll().stream().toList();
    }

    @Override
    @Transactional
    public void deleteAircraft(Long id) {
        aircraftService.deleteById(id);
    }

    @Override
    @Transactional
    public void patchAircraft(Aircraft aircraft) {
        aircraftService.update(aircraft);
    }

    @Override
    @Transactional
    public void saveFlight(Flight flight) {
        flightService.createFlight(flight);
    }

    @Override
    public Flight findFlightById(Long id) {
        return flightService.findFlightById(id);
    }

    @Override
    public List<Flight> findAllFlight() {
        return flightService.findAll().stream().toList();
    }

    @Override
    @Transactional
    public void deleteFlight(Long id) {
        flightService.deleteFlight(id);
    }

    @Override
    @Transactional
    public void patchFlight(Flight flight) {
        flightService.updateFlight(flight);
    }

    @Override
    public List<Ticket> findAllSoldTicket() {
        return ticketService.findTicketsByPassengerNotNull();
    }

    @Override
    public List<TicketDTO> findAllSoldTicketWithStatus() {
        List<TicketDTO> list = new ArrayList<>();
        List<Ticket> soldTicket = findAllSoldTicket();
        for (Ticket ticket : soldTicket) {
            list.add(new TicketDTO(ticket.getId(),
                    ticketService.isPassengerRegistered(ticket),
                    ticketService.isFlightCompleted(ticket)));
        }
        return list;
    }

}
