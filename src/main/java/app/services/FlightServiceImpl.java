package app.services;

import app.entities.Flight;
import app.entities.Seat;
import app.repositories.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation FlightService - Application Business Logic
 *
 * @author - Alexander Plekhov
 */
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional
    public void createFlight(Flight flight) {
        flightRepository.save(flight);
    }

    @Override
    @Transactional
    public void updateFlight(Flight flight) {
        Flight updatedFlight = flightRepository.findFlightById(flight.getId());
        updatedFlight.setAircraft(flight.getAircraft());
        updatedFlight.setTo(flight.getTo());
        updatedFlight.setFrom(flight.getFrom());
        updatedFlight.setArrivalDateTime(flight.getArrivalDateTime());
        updatedFlight.setDepartureDateTime(flight.getDepartureDateTime());
        flightRepository.save(updatedFlight);
    }

    @Override
    @Transactional(readOnly = true)
    public Flight findFlightById(Long id) {
        return flightRepository.findFlightById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findFlightByFromToDate(String from, String to, String date) {
        List<Flight> listWithFromTo = flightRepository.findAllFlightByFromAndTo(from, to);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        return listWithFromTo.stream()
                .filter(x -> x.getDepartureDateTime().format(formatter).equals(date))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findFlightByDepartureDateTime(LocalDateTime localDateTime) {
        return flightRepository.findAllFlightByDepartureDateTime(localDateTime);
    }

    //need to be finalized
    @Override
    @Transactional(readOnly = true)
    public List<Seat> findAllFreeSeatsOnFlight(Flight flight) {
        return null;
    }

    //need to be finalized
    @Override
    @Transactional(readOnly = true)
    public List<Seat> findAllFreeSeatsOnFlightByEconomy(Flight flight) {
        return null;
    }

    //need to be finalized
    @Override
    @Transactional(readOnly = true)
    public List<Seat> findAllFreeSeatsOnFlightByBusiness(Flight flight) {
        return null;
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

}
