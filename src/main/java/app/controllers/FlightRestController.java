package app.controllers;

import app.entities.Flight;
import app.entities.Seat;
import app.services.FlightService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-Controller Flight, includes:
 * CRUD
 * getting Flight by ID;
 * getting Flight by from/to/date;
 * getting all free Seats on the Flight
 * getting free Seats on the Flight by Category Economy
 * getting free Seats on the Flight by Category Business
 *
 * @author Babkin Artem
 */

@RestController
@RequestMapping("/api/flights")
public class FlightRestController {

    static final Logger logger = LogManager.getLogger(FlightRestController.class);

    private final FlightService flightService;
    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable("id") long id) {
        Flight flight = flightService.findFlightById(id);
        if (flight == null) {
            logger.warn("Flight not found (findFlightById): " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("show Flight by id: " + id);
        return ResponseEntity.ok(flight);
    }

    @PostMapping()
    public ResponseEntity<Flight> createFLight(@RequestBody Flight flight) {
        flightService.createFlight(flight);
        logger.info("Flight created:" + flight.getId());
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight) {
        flightService.updateFlight(flight);
        logger.info("Flight updated:" + flight.getId());
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @GetMapping("/{from}/{to}/{date}")
    public ResponseEntity<List<Flight>> findFlightByFromToDate(@PathVariable("from") String from,
                                                               @PathVariable("to") String to,
                                                               @PathVariable("date") String date) {
        List<Flight> flight = flightService.findFlightByFromToDate(from, to, date);
        if (flight.isEmpty()) {
            logger.warn("Flight not found (findFlightByFromToDate): " + from + "/" + to + "/" + date);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Flight found: " + from + "/" + to + "/" + date);
        return ResponseEntity.ok(flight);
    }
    @GetMapping("/allFree")
    public ResponseEntity<List<Seat>> findAllFreeSeatsOnFlight(@RequestBody Flight flight) {
        List<Seat> listSeat = flightService.findAllFreeSeatsOnFlight(flight);
        if (listSeat.isEmpty()) {
            logger.warn("listSeat does not exist (findAllFreeSeatsOnFlight): " + flight.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("All free seats on flight found: " + flight.getId());
        return ResponseEntity.ok(listSeat);
    }

    @GetMapping("/allFreeEconomy")
    public ResponseEntity<List<Seat>> findAllFreeSeatsOnFlightByEconomy(@RequestBody Flight flight) {
        List<Seat> listSeatByEconomy = flightService.findAllFreeSeatsOnFlightByEconomy(flight);
        if (listSeatByEconomy.isEmpty()) {
            logger.warn("listSeatByEconomy does not exist (findAllFreeSeatsOnFlightByEconomy): " + flight.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("All free seats by economy on flight found: " + flight.getId());
        return ResponseEntity.ok(listSeatByEconomy);
    }

    @GetMapping("/allFreeBusiness")
    public ResponseEntity<List<Seat>> findAllFreeSeatsOnFlightByBusiness(@RequestBody Flight flight) {
        List<Seat> listSeatByBusiness = flightService.findAllFreeSeatsOnFlightByBusiness(flight);
        if (listSeatByBusiness.isEmpty()) {
            logger.warn("listSeatByBusiness does not exist (findAllFreeSeatsOnFlightByBusiness): " + flight.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("All free seats by business on flight found: " + flight.getId());
        return ResponseEntity.ok(listSeatByBusiness);
    }

}
