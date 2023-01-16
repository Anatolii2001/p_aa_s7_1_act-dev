package app.config;


import app.entities.*;
import app.repositories.DestinationRepository;
import app.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */

@AllArgsConstructor
@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final DestinationService destinationService;
    private final AircraftService aircraftService;
    private final FlightService flightService;
    private final PassengerService passengerService;
    private final SeatService seatService;
    private final SearchResultService searchResultService;
    private final TicketService ticketService;
    private final DestinationRepository destinationRepository;

    @PostConstruct
    public void init() {
        initRoles();
        createUserForSpringSecurity();
        createDestination();
        createAircraft();
        createFlight();
        createTestPassenger();
        createTestTicket();
//        createTickets();


        //createTestSearch();

        System.out.println("DataInitializer сработал!");
    }

    /**
     * Method for initialization destination
     */
    public void createDestination() {
        Destination destination = new Destination("New York", "United States", "America");
        Destination destination2 = new Destination("Cairo", "Egypt", "Africa");
        Destination destination3 = new Destination("Stockholm", "Sweden", "Europe");

        destinationService.saveDestination(destination);
        destinationService.saveDestination(destination2);
        destinationService.saveDestination(destination3);
    }

    /**
     * Метод для инициализации ролей
     */
    public void initRoles() {
        Role admin = new Role("ROLE_ADMIN");
        Role manager = new Role("ROLE_MANAGER");
        Role passenger = new Role("ROLE_PASSENGER");
        roleService.saveRole(admin);
        roleService.saveRole(manager);
        roleService.saveRole(passenger);
    }

    /**
     * Метод для создания юзера для SpringSecurity
     */
    public void createUserForSpringSecurity() {
        User admin = new Admin();
        admin.setEmail("admin@email.com");
        admin.setPassword("admin");
        admin.addRoleToCollection(roleService.findRoleByName("ROLE_ADMIN"));
        userService.saveUser(admin);

        User manager = new AirlineManager();
        manager.setEmail("manager@email.com");
        manager.setPassword("manager");
        manager.addRoleToCollection(roleService.findRoleByName("ROLE_MANAGER"));
        userService.saveUser(manager);


    }

    /**
     * Create 3 Aircraft in DB
     */

    public void createAircraft() {

        aircraftService.save(new Fleet().createBoeing737());
        aircraftService.save(new Fleet().createAirbusA321());
        aircraftService.save(new Fleet().createEmbraer170());

    }

    public void createFlight() {

        Flight flight1 = new Flight();
        flight1.setAircraft(aircraftService.findById(1L).get());
        flight1.setDepartureDateTime(LocalDateTime.of(2022, Month.OCTOBER, 30, 11, 30));
        flight1.setArrivalDateTime(LocalDateTime.of(2022, Month.OCTOBER, 30, 18, 00));
        flight1.setFrom(destinationService.getDestinationById(1L));
        flight1.setTo(destinationService.getDestinationById(2L));
        flight1.setFlightStatus(Flight.FlightStatus.ON_TIME);

        Flight flight2 = new Flight();
        flight2.setAircraft(aircraftService.findById(2L).get());
        flight2.setDepartureDateTime(LocalDateTime.of(2022, Month.OCTOBER, 30, 9, 30));
        flight2.setArrivalDateTime(LocalDateTime.of(2022, Month.OCTOBER, 30, 16, 20));
        flight2.setFrom(destinationService.getDestinationById(1L));
        flight2.setTo(destinationService.getDestinationById(2L));
        flight2.setFlightStatus(Flight.FlightStatus.ON_TIME);

        Flight flight3 = new Flight();
        flight3.setAircraft(aircraftService.findById(3L).get());
        flight3.setDepartureDateTime(LocalDateTime.of(2022, Month.OCTOBER, 31, 12, 30));
        flight3.setArrivalDateTime(LocalDateTime.of(2022, Month.OCTOBER, 31, 16, 20));
        flight3.setFrom(destinationService.getDestinationById(2L));
        flight3.setTo(destinationService.getDestinationById(3L));
        flight3.setFlightStatus(Flight.FlightStatus.ON_TIME);

        Flight flight4 = new Flight();
        Aircraft aircraft4 = new Fleet().createEmbraer170();
        aircraft4.setBoardNumber("00012BB");
        aircraftService.save(aircraft4);
        flight4.setAircraft(aircraftService.findByBoardNumber("00012BB").get());
        flight4.setDepartureDateTime(LocalDateTime.of(2022, Month.NOVEMBER, 5, 07, 30));
        flight4.setArrivalDateTime(LocalDateTime.of(2022, Month.NOVEMBER, 5, 11, 20));
        flight4.setFrom(destinationService.getDestinationById(3L));
        flight4.setTo(destinationService.getDestinationById(2L));
        flight4.setFlightStatus(Flight.FlightStatus.DELAYED);

        Flight flight5 = new Flight();
        Aircraft aircraft5 = new Fleet().createAirbusA321();
        aircraft5.setBoardNumber("00108BB");
        aircraftService.save(aircraft5);
        flight5.setAircraft(aircraftService.findByBoardNumber("00108BB").get());
        flight5.setDepartureDateTime(LocalDateTime.of(2022, Month.NOVEMBER, 7, 11, 30));
        flight5.setArrivalDateTime(LocalDateTime.of(2022, Month.NOVEMBER, 7, 18, 00));
        flight5.setFrom(destinationService.getDestinationById(1L));
        flight5.setTo(destinationService.getDestinationById(2L));
        flight5.setFlightStatus(Flight.FlightStatus.ON_TIME);

        flightService.createFlight(flight1);
        flightService.createFlight(flight2);
        flightService.createFlight(flight3);
        flightService.createFlight(flight4);
        flightService.createFlight(flight5);

/**
 * Create from 1 to 6 flights for each day of September 2022
 * with random aircraft and random fare of seats
 */
        for (int i = 1; i <= 30; i++) {
            int rand = 1 + (int) (Math.random() * 5);
            for (int j = 1; j <= rand; j++) {
                Flight flight = new Flight();
                Aircraft aircraft = new Aircraft();
                if (j < 2) {
                    aircraft = new Fleet().createAirbusA321();
                } else if (j <= 3) {
                    aircraft = new Fleet().createEmbraer170();
                } else {
                    aircraft = new Fleet().createBoeing737();
                }
                int fare = ((int) (Math.random() * 50)) * 10 + 509;
                for (Seat s : aircraft.getSeats()) {
                    s.setFare(fare);
                }
                String sideNumber = "00000" + i + j + "RND";
                aircraft.setBoardNumber(sideNumber);
                aircraftService.save(aircraft);
                flight.setAircraft(aircraftService.findByBoardNumber(sideNumber).get());
                int hour = (int) (Math.random() * 12);
                flight.setDepartureDateTime(LocalDateTime.of(2022, Month.SEPTEMBER, i, hour, 20));
                flight.setArrivalDateTime(LocalDateTime.of(2022, Month.SEPTEMBER, i, hour + 6, 30));
                flight.setFrom(destinationService.getDestinationById(1L));
                flight.setTo(destinationService.getDestinationById(2L));
                flight.setFlightStatus(Flight.FlightStatus.ON_TIME);
                flightService.createFlight(flight);
            }
        }
    }

    public void createTestPassenger() {
        Passenger passenger = new Passenger();
        passenger.setEmail("plekhov.a.dm@gmail.com");
        passenger.setPassword("pass");
        passenger.setFirstName("Morgan");
        passenger.setLastName("Freeman");
        passenger.setMiddleName("Freemanovich");
        passenger.setPhoneNumber("+7-913-937-77-77");
        passenger.setDateOfBirth(LocalDate.of(1937, Month.JUNE, 1));
        passenger.setPassport(new Passport("1234 567890", "2022-11-22", "Russian"));
        passenger.addRoleToCollection(roleService.findRoleByName("ROLE_PASSENGER"));
        passengerService.savePassenger(passenger);
    }

    public void createTestTicket() {
        Ticket ticket = new Ticket();
        ticket.setFlight(flightService.findFlightById(1L));
        ticket.setPassenger(passengerService.getPassenger(3L));
        ticket.setSubcategory(Ticket.Subcategory.ECONOMY_CLASS);
        ticket.setSeat(seatService.findById(23L).get());
        ticketService.saveTicket(ticket);
    }
}