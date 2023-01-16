package app.controllers;

import app.entities.Passenger;
import app.services.PassengerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passenger")
public class PassengerRESTController {

    static final Logger logger = LogManager.getLogger(PassengerRESTController.class);
    private final PassengerService passengerService;

    @Autowired
    public PassengerRESTController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping()
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        logger.info("get all Passengers ");
        return ResponseEntity.ok(passengerService.getAllPassenger());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable long id) {
        Optional<Passenger> passenger = Optional.ofNullable(passengerService.getPassenger(id));
        if (passenger == null) {
            logger.warn("Passenger not found (getPassenger): " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found");
        }
        logger.info("Show Passenger: " + id);
        return passenger.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<Passenger> addNewPassenger(@RequestBody Passenger passenger) {
        passengerService.savePassenger(passenger);
        logger.info("Passenger created: " + passenger.getId());
        return ResponseEntity.ok(passenger);
    }

    @PutMapping()
    public ResponseEntity<Passenger> editPassenger(@RequestBody Passenger passenger) {
        if (passengerService.getPassenger(passenger.getId()) == null) {
            logger.warn("Passenger does not exist: " + passenger.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found");
        }
        logger.info("Passenger updated: " + passenger.getId());
            return  ResponseEntity.ok(passengerService.update(passenger));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Passenger> deletePassenger(@PathVariable long id) {
        Optional<Passenger> passenger = Optional.ofNullable(passengerService.getPassenger(id));

        if (passenger.isEmpty()) {
            logger.warn("Passenger does not exist: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            passengerService.deletePassenger(id);
            logger.info("Passenger deleted: " + id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}