package app.controllers;

import app.entities.Destination;
import app.services.DestinationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controllers for Destination
 * @author Andrey Mitukov
 * @author Olga Maslova
 */

@RestController
@RequestMapping("/api/destination")
public class DestinationRestController {

    static final Logger logger = LogManager.getLogger(DestinationRestController.class);
    private final DestinationService destinationService;

    public DestinationRestController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

  @GetMapping
 public ResponseEntity<List<Destination>> getAllDestinations() {
      logger.info("show all Destinations");
        return new ResponseEntity<>(destinationService.getAllDestinations(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
      logger.info("Destination created");
        return new ResponseEntity<>(destinationService.saveDestination(destination), HttpStatus.CREATED);
  }

 @PutMapping("/{id}")
  public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination) {
     if(destination == null) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
     destinationService.updateDestination(destination);
     logger.info("Destinations updated: " + destination.getId());
     return new ResponseEntity<>(destination, HttpStatus.OK);
 }

 @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable("id") Long id) {
     Destination destination = destinationService.getDestinationById(id);
     if(destination == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
     logger.info("show destination by id: " + id);
     return new ResponseEntity<>(destination, HttpStatus.OK);
 }

 @GetMapping("/city")
    public ResponseEntity<Destination> getDestinationByCity(@RequestParam (value = "city", required = false) String city) {
     logger.info("show destination  by city: " + city);
        return new ResponseEntity<>(destinationService.getDestinationByCity(city), HttpStatus.OK);
 }
 @GetMapping("/country")
    public ResponseEntity<Destination> getDestinationByCountry(@RequestParam(value = "countryName", required = false) String countryName) {
     logger.info("show destination  by country: " + countryName);
        return new ResponseEntity<>(destinationService.getDestinationByCountryName(countryName), HttpStatus.OK);
 }

    @DeleteMapping
    public void deleteAllDestination () {
        logger.info("Destinations delete");
        destinationService.deleteAllDestination();
    }
 }











