package app.controllers;

import app.entities.Aircraft;
import app.services.AircraftService;
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

import java.util.List;

/**
 * REST controller for {@link Aircraft} entity
 *
 * @author Shchepin Maksim
 */

@Api
@Log4j2
@RestController
@CrossOrigin
@RequestMapping("/api/aircrafts")
public class AircraftRestController {

    static final Logger logger = LogManager.getLogger(AircraftRestController.class);
    private final AircraftService aircraftService;

    @Autowired
    public AircraftRestController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ApiOperation("Get an aircraft by id")
    @GetMapping("/id={id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftService.findById(id)
                .map(x -> {
                    logger.info("Got aircraft by id={}", id);
                    return ResponseEntity.ok(x);})
                .orElseThrow(() -> {
                    ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No aircraft with id " + id + " was found.");
                    logger.warn("Aircraft not found by id={}", id, ex);
                    return ex;
                });
    }

    @ApiOperation("Get an aircraft by board number")
    @GetMapping("/board={boardNumber}")
    public ResponseEntity<Aircraft> getAircraftByBoardNumber(@PathVariable String boardNumber) {
        return aircraftService.findByBoardNumber(boardNumber)
                .map(x -> {
                    logger.info("Got aircraft by board number={}", boardNumber);
                    return ResponseEntity.ok(x);
                })
                .orElseThrow(() -> {
                    ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No aircraft with board number " + boardNumber + " was found.");
                    logger.warn("Aircraft not found by board number={}", boardNumber, ex);
                    return ex;
                });
    }

    @ApiOperation("Get all aircrafts")
    @GetMapping()
    public ResponseEntity<List<Aircraft>> getAllAircrafts() {
        List<Aircraft> result = aircraftService.findAll();
        HttpStatus resultStatus;
        if (result.size() != 0) {
            logger.info("Got all {} aircrafts", result.size());
            resultStatus = HttpStatus.OK;
        } else {
            logger.info("There are no aircrafts in the repository");
            resultStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(result, resultStatus);
    }

    @ApiOperation("Flexibly aircraft's search by combine of not required parameters" +
            "[brand, model, manufacturedYear, flyingRange].")
    @GetMapping("/search")
    public List<Aircraft> flexAircraftSearch(@RequestParam(required = false) String brand,
                                             @RequestParam(required = false) String model,
                                             @RequestParam(required = false) Integer year,
                                             @RequestParam(required = false) Integer range) {
        List<Aircraft> result = aircraftService.searchByParams(brand, model, year, range);
        logger.info("Flexibly found {} aircrafts by brand={}, model={}, prod year={}, fly range={}",
                result.size(), brand, model, year, range);
        return result;
    }

    @ApiOperation("Create a new aircraft")
    @PostMapping()
    public ResponseEntity<Aircraft> addAircraft(@RequestBody Aircraft aircraft) {
        Aircraft result;
        logger.info("Adding a new {}", aircraft);
        try {
            result = aircraftService.save(aircraft);
        } catch (IllegalArgumentException ex) {
            String errorMsg = "Incorrect data for this request: only aircraft with id = 0 can be passed in" +
                    " with the \"POST\" method instead of aircraft=" + aircraft;
            logger.warn(errorMsg, ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMsg, ex);
        }
        logger.info("Successfully added new aircraft, id={}", result.getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @ApiOperation("Update aircraft's parameters")
    @PutMapping()
    public ResponseEntity<Aircraft> updateAircraft(@RequestBody Aircraft aircraft) {
        Aircraft result;
        logger.info("Updating {}", aircraft);
        try {
            result = aircraftService.update(aircraft);
        } catch (IllegalArgumentException ex) {
            String errorMsg = "Incorrect data for this request: only existing aircrafts found by id can be passed in" +
                    " with the \"PUT\" method instead of aircraft=" + aircraft;
            logger.warn(errorMsg, ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMsg, ex);
        }
        logger.info("Successfully updated aircraft with id={}.", result.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation("Delete an aircraft by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAircraft(@PathVariable Long id) {
        if (aircraftService.deleteById(id)) {
            logger.info("Successfully deleted aircraft with id={}", id);
            return ResponseEntity.ok(true);
        } else {
            logger.warn("Failed try to delete aircraft with id={}, aircraft not found", id);
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Delete all aircrafts: clear aircraft's repository")
    @DeleteMapping()
    public ResponseEntity<String> deleteAllAircrafts() {
        aircraftService.deleteAll();
        String msg = "Successfully deleted all aircrafts";
        logger.info(msg);
        return ResponseEntity.ok(msg);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleException(ResponseStatusException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }

}
