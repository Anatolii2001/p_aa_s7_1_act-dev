package app.services;

import app.entities.Flight;
import app.entities.Seat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Flight Service
 *
 * @author - Babkin Artyom
 */

public interface FlightService {
    void createFlight(Flight flight);
    void updateFlight(Flight flight);
    Flight findFlightById(Long id);
    List<Flight> findFlightByFromToDate(String from, String to, String date);
    List<Flight> findFlightByDepartureDateTime(LocalDateTime localDateTime);
    List<Seat> findAllFreeSeatsOnFlight(Flight flight);
    List<Seat> findAllFreeSeatsOnFlightByEconomy(Flight flight);
    List<Seat> findAllFreeSeatsOnFlightByBusiness(Flight flight);

    List<Flight> findAll();

    void deleteFlight(Long id);


}
