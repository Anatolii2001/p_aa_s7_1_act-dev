package app.services;

import app.entities.Aircraft;
import app.entities.Flight;
import app.entities.Ticket;
import app.entities.TicketDTO;

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

public interface AirlineManagerService {


    // Управение авиапарком

    void saveAircraft(Aircraft aircraft);

    Aircraft findAircraftById(Long id);

    List<Aircraft> findAllAircraft();

    void deleteAircraft(Long id);

    void patchAircraft(Aircraft aircraft);

    // Управение рейсами

    void saveFlight(Flight flight);

    Flight findFlightById(Long id);

    List<Flight> findAllFlight();

    void deleteFlight(Long id);

    void patchFlight(Flight flight);

    // Управение билетами

    // выводим список проданных билетов, проверяем по наличию пассажира
    List<Ticket> findAllSoldTicket();

    //видим проданные билеты + статус

    List<TicketDTO> findAllSoldTicketWithStatus();


}
