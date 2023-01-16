package app.controllers;

import app.entities.CategoryType;
import app.entities.Seat;
import app.services.SeatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

/**
 * REST controller for {@link Seat} entity
 *
 * @author Shchepin Maksim
 */

@Api
@Log4j2
@RestController
@CrossOrigin
@RequestMapping("/api")
public class SeatRestController {

    static final Logger logger = LogManager.getLogger(SeatRestController.class);
    private final SeatService seatService;

    @Autowired
    public SeatRestController(SeatService seatService) {
        this.seatService = seatService;
    }

    @ApiOperation("Get a seat by id")
    @GetMapping("/seats/{id}")
    public ResponseEntity<Seat> getSeat(@PathVariable Long id) {
        Optional<Seat> result = seatService.findById(id);
        if (result.isEmpty()) {
            String error = String.format("Not found seat with id = %d", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
        logger.info("Found seat with id = {}", id);
        return ResponseEntity.ok(result.get());
    }

    @ApiOperation("Get all seats: all from seats table")
    @GetMapping("/seats")
    public Set<Seat> getAllSeats() {
        Set<Seat> result = seatService.findAll();
        logger.info("Found {} seats", result.size());
        return result;
    }

    @ApiOperation("Create new seat")
    @PostMapping("/seats")
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) {
        Seat result;
        try{
            result = seatService.save(seat);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Failed to save seat with id = %d", seat.getId());
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error, ex);
        }
        logger.info("Seat with id = {} successfully saved.", result.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation("Update seat parameters")
    @PutMapping("/seats")
    public ResponseEntity<Seat> updateSeat(@RequestBody Seat seat) {
        Seat result;
        try{
            result = seatService.update(seat);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Failed to update seat with id = %d", seat.getId());
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error, ex);
        }
        logger.info("Seat with id = {} successfully updated.", result.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation("Delete a seat by id")
    @DeleteMapping("/seats/{id}")
    public ResponseEntity<Boolean> deleteSeat(@PathVariable Long id) {
        boolean result = seatService.deleteById(id);
        if (!result) {
            String error = String.format("Not found seat with id = %d", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
        return ResponseEntity.ok(true);
    }

    @ApiOperation("Delete all seats: clear seats table")
    @DeleteMapping("/seats")
    public void deleteAllSeats() {
        seatService.deleteAll();
        logger.info("All seats deleted.");
    }

    @ApiOperation("Get all seats by flight id")
    @GetMapping("/seats/flight/{id}")
    public Set<Seat> getAllSeatsByFlightId(@PathVariable Long id) {
        Set<Seat> result;

        try{
            result = seatService.findAllByFlightId(id);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Flight with id = %d not found.", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error, ex);
        }

        logger.info("Flight_id = {}, seats found = {}", id, result.size());
        return result;
    }

    @ApiOperation("Get all seats by flight id and category")
    @GetMapping("/seats/category/{category}/flight/{id}")
    public Set<Seat> getSeatByFlightIdAndCategory(@PathVariable CategoryType category, @PathVariable Long id) {
        Set<Seat> result;

        try{
            result = seatService.findAllByFlightIdAndCategory(id, category);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Flight with id = %d not found.", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error, ex);
        }

        logger.info("Flight_id = {}, category = {}, seats found = {}", id, category, result.size());
        return result;
    }

    @ApiOperation("Get number of sold seats on the flight by id")
    @GetMapping("/seats/sold/flight/{id}")
    public int getSeatsSoldCountByFlightId(@PathVariable Long id) {
        int result;

        try{
            result = seatService.getSoldNumberByFlightId(id);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Flight with id = %d not found.", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error, ex);
        }

        logger.info("Flight_id = {}, sold seats number = {}", id, result);
        return result;

    }

    @ApiOperation("Get number of unsold seats on the flight by id")
    @GetMapping("/seats/unsold/flight/{id}")
    public int getSeatsUnsoldCountByFlightId(@PathVariable Long id) {
        int result;

        try{
            result = seatService.getUnsoldNumberByFlightId(id);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Flight with id = %d not found.", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error, ex);
        }

        logger.info("Flight_id = {}, unsold seats number = {}", id, result);
        return result;
    }

    @ApiOperation("Get number of registered seats on the flight by id")
    @GetMapping("/seats/registered/flight/{id}")
    public int getSeatsRegCountByFlightId(@PathVariable Long id) {
        int result;
        try{
            result = seatService.getRegisteredNumberByFlightId(id);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Flight with id = %d not found.", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error, ex);
        }
        logger.info("Flight_id = {}, registered seats number = {}", id, result);
        return result;
    }

    @ApiOperation("Get seat by seat number and flight id")
    @GetMapping("/seats/{seatNumber}/flight/{id}")
    public ResponseEntity<Seat> getSeatByFlightIdAndSeatNumber(@PathVariable Long id, @PathVariable String seatNumber) {
        Optional<Seat> seat;

        try {
            seat = seatService.findByFlightIdAndSeatNumber(id, seatNumber);
        } catch (IllegalArgumentException ex) {
            String error = String.format("Flight with id = %d not found.", id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error, ex);
        }

        if (seat.isEmpty()) {
            String error = String.format("Not found seat with seat_number= %s in flight with flight_id = %d",
                    seatNumber, id);
            logger.warn(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
        logger.info("Found seat with id = {} by flight_id = {} and seat_number = {}",
                seat.get().getId(), id, seatNumber);
        return ResponseEntity.ok(seat.get());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleException(ResponseStatusException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }

}
