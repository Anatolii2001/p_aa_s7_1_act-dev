package app.controllers;

import app.entities.Passport;
import app.services.PassportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PassportRestController {

    static final Logger logger = LogManager.getLogger(PassportRestController.class);
    private final PassportService passportService;

    @Autowired
    public PassportRestController(PassportService passportService) {
        this.passportService = passportService;
    }

    @GetMapping(value = "/passports")
    public List<Passport> findAll() {
        logger.info("find all Passports");
        return passportService.findAllPassports();
    }

    @GetMapping("/passports/{id}")
    public Optional<Passport> getOnePassport(@PathVariable long id) {
        Optional<Passport> passport = passportService.getOnePassport(id);
        if (passport == null) {
            logger.warn("Passport not found (getPassport): " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found");
        }
        logger.info("Show Passport: " + id);
        return passportService.getOnePassport(id);
    }

    @PostMapping("/passports")
    public Passport insertPassport(@RequestBody Passport passport) {
        logger.info("Passport created: " + passport.getId());
        passportService.insertPassport(passport);
        return passport;
    }

    @PutMapping("/passports")
    public Passport updatePassport(@RequestBody Passport passport) {
        if (passportService.getOnePassport(passport.getId()) == null) {
            logger.warn("Passport does not exist: " + passport.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found");
        }
        logger.info("Passport updated: " + passport.getId());
        passportService.updatePassport(passport);
        return passport;
    }

    @DeleteMapping("/passports/{id}")
    public String deletePassport(@PathVariable long id) {
        Optional<Passport> passport = passportService.getOnePassport(id);
        if (passport == null) {
            logger.warn("Passport does not exist: " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found");
        }
        passportService.deletePassport(id);
        logger.info("Passport deleted: " + id);
        return "Employee with ID = " + id + " was deleted";
    }
}