//package app;
//
//import app.controllers.AircraftRestController;
//import app.entities.*;
//import app.repositories.*;
//import app.services.*;
//import app.util.Category;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.*;
//import org.springframework.test.web.servlet.MockMvc;
//import java.util.*;
//import java.util.stream.Collectors;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc(addFilters = false)
//public class SeatRestControllerTests {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private FlightService flightService;
//    @Autowired
//    private AircraftService aircraftService;
//    @Autowired
//    private FlightRepository flightRepository;
//    @Autowired
//    private DestinationRepository destinationRepository;
//    @Autowired
//    private AircraftRepository aircraftRepository;
//
//
//    @BeforeEach
//    public void resetDB() {
//        flightRepository.deleteAll();
//        destinationRepository.deleteAll();
//        aircraftRepository.deleteAll();
//    }
//
//    /**
//     * Get all Seats test
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenGetAllSeats_thenStatus200() throws Exception {
//        Flight flight1 = createFlight1();
//        Flight flight2 = createFlight2();
//        Set<Seat> seats = flight1.getAircraft().getSeats();
//        seats.addAll(flight2.getAircraft().getSeats());
//
//        mockMvc.perform(
//                        get("/api/seats"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(seats)));
//    }
//
//    /**
//     * Get Seat by id test
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenGetSeatById_thenStatus200() throws Exception {
//        Flight flight = createFlight2();
//        List<Seat> seats = new ArrayList<>(flight.getAircraft().getSeats());
//        for (Seat s : seats) {
//            long id = s.getId();
//
//            mockMvc.perform(
//                            get("/api/seats/{id}", id))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.id").value(id))
//                    .andExpect(jsonPath("$.isSold").value(false));
//        }
//    }
//
//
//    /**
//     * Update Seat test
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenUpdateSeat_thenStatus200() throws Exception {
//
//        Flight flight = createFlight2();
//        List<Seat> updatedSeats = new ArrayList<>(flight.getAircraft().getSeats());
//        for (Seat s : updatedSeats) {
//            s.setFare(1000);
//
//            mockMvc.perform(
//                            put("/api/seats")
//                                    .content(objectMapper.writeValueAsString(s))
//                                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.isSold").value(false))
//                    .andExpect(jsonPath("$.fare").value(1000));
//        }
//    }
//
//
//    /**
//     * Get Seats by flight id test
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenGetSeatsByFlightId_thenStatus200() throws Exception {
//        Flight flight = createFlight2();
//        long id = flight.getId();
//        Set<Seat> seats = flight.getAircraft().getSeats();
//
//        mockMvc.perform(
//                        get("/api/seats/flight/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(seats)));
//    }
//
//
//    /**
//     * Get Seats by flight id & Category test
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenGetSeatsByFlightIdAndCategory_thenStatus200() throws Exception {
//        Flight flight = createFlight2();
//        long id = flight.getId();
//        String category = Category.BUSINESS.toString();
//        List<Seat> seats = flight.getAircraft().getSeats()
//                .stream().filter(s -> s.getCategory().equals(Category.BUSINESS)).collect(Collectors.toList());
//
//        mockMvc.perform(
//                        get("/api/seats/flight/{id}/category/{category}", id, category))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(seats)));
//    }
//
//    /**
//     * Get Seats by flight id & sales test
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenGetSeatsByFlightIdAndSales_thenStatus200() throws Exception {
//        Flight flight = createFlight2();
//        long id = flight.getId();
//        Boolean isSold = false;
//        List<Seat> seats = flight.getAircraft().getSeats()
//                .stream().filter(s -> s.getIsSold().equals(false)).collect(Collectors.toList());
//
//        mockMvc.perform(
//                        get("/api/seats/flight/{id}/isSold/{isSold}", id, isSold))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(seats)));
//    }
//
//
//    /**
//     * Get Seats by flight id & sales test
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenGetSeatsByFlightIdAndRegistration_thenStatus200() throws Exception {
//        Flight flight = createFlight2();
//        long id = flight.getId();
//        Boolean isRegistered = false;
//        List<Seat> seats = flight.getAircraft().getSeats()
//                .stream().filter(s -> s.getIsRegistered().equals(false)).collect(Collectors.toList());
//
//        mockMvc.perform(
//                        get("/api/seats/flight/{id}/isRegistered/{isRegistered}", id, isRegistered))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(seats)));
//    }
//
//
//    /**
//     * Helper methods for create flights and aircraft
//     */
//
//    private Aircraft createTestAircraft1() {
//        Aircraft aircraft = new Fleet().createBoeing737(); // Seats size() = 180
//        aircraftService.save(aircraft);
//        return aircraft;
//    }
//
//    private Aircraft createTestAircraft2() {
//        Aircraft aircraft = new Fleet().createAirbusA321(); // Seats size() = 216
//        aircraftService.save(aircraft);
//        return aircraft;
//    }
//
//    public Flight createFlight1() {
//        Flight flight = new Flight();
//        flight.setAircraft(createTestAircraft1());
//        flightService.save(flight);
//        return flight;
//    }
//
//    public Flight createFlight2() {
//        Flight flight = new Flight();
//        flight.setAircraft(createTestAircraft2());
//        flightService.save(flight);
//        return flight;
//    }
//}
