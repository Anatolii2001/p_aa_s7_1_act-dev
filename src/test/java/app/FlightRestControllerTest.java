//package app;
//
//import app.entities.Aircraft;
//import app.entities.Destination;
//import app.entities.Flight;
//import app.repositories.AircraftRepository;
//import app.repositories.DestinationRepository;
//import app.repositories.FlightRepository;
//import app.services.Fleet;
//import app.services.FlightService;
//import app.util.Category;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.thymeleaf.standard.expression.Each;
//
//import java.time.LocalDateTime;
//import java.time.Month;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * test FlightRestController with MockMVC
// *
// * @author - Alexander PLekhov
// */
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc(addFilters = false) //added (addFilters = false)
//public class FlightRestControllerTest {
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private FlightService flightService;
//    @Autowired
//    private FlightRepository flightRepository;
//    @Autowired
//    private DestinationRepository destinationRepository;
//    @Autowired
//    private AircraftRepository aircraftRepository;
//
//    @BeforeEach // added
//    @AfterEach
//    public void resetDB(){
//        flightRepository.deleteAll();
//        destinationRepository.deleteAll();
//        aircraftRepository.deleteAll();
//    }
//
//    private Flight createTestFlight() {
//        Destination moscow = new Destination("Moscow", "Russian Federation", "Europe"); // changed fields
//        Destination kiev = new Destination("Kiev", "Ukraine", "Europe"); // changed fields
//        Aircraft boeing = new Fleet().createBoeing737(); // changed method. brand = "Boeing"; name/sideNumber = "00001AA"
//
//        destinationRepository.save(moscow);
//        destinationRepository.save(kiev);
//        aircraftRepository.save(boeing);
//
//        Flight flight = new Flight();
//        flight.setFrom(destinationRepository.findDestinationByCity("Moscow").get()); // changed findDestinationByName() to findDestinationByCity().get()
//        flight.setTo(destinationRepository.findDestinationByCity("Kiev").get()); // changed findDestinationByName() to findDestinationByCity().get()
//        flight.setDepartureDateTime(LocalDateTime.of(2022, Month.AUGUST, 9, 13, 00, 00));
//        flight.setArrivalDateTime(LocalDateTime.of(2022, Month.AUGUST, 9, 15, 00, 00));
//        flight.setAircraft(aircraftRepository.findAircraftByName("00001AA")); // changed name of aircraft
//        flight.setFlightStatus(Flight.FlightStatus.ON_TIME);
//
//        flightService.save(flight);
//
//        return flight;
//    }
//
//    @Test
//    public void whenCreateFlight_thenStatus201() throws Exception {
//        Flight flight = createTestFlight();
//        mockMvc.perform(
//                post("/api/flights")
//                        .content(objectMapper.writeValueAsString(flight))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber());
//    }
//
//    @Test
//    public void whenUpdateFlight_thenStatus200() throws Exception {
//        Flight flight = createTestFlight();
//        Aircraft airbus = new Fleet().createAirbusA321(); // changed method. brand = "Airbus"; name/sideNumber = "00002AA"
//        aircraftRepository.save(airbus);
//        flight.setAircraft(aircraftRepository.findAircraftByName("00002AA"));
//
//        mockMvc.perform(
//                put("/api/flights")
//                        .content(objectMapper.writeValueAsString(flight))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.aircraft.name").value("00002AA")); //changed name = "00002AA"
//    }
//    @Test
//    public void whenDeleteFlight_thenStatus200() throws Exception {
//        Flight flight = createTestFlight();
//        long id = flight.getId();
//
//        mockMvc.perform(
//                delete("/api/flights/{id}", id)
//                        .content(objectMapper.writeValueAsString(flight))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id));
//    }
//
//    @Test
//    public void whenGetFlightById_thenStatus200() throws Exception {
//        Flight flight = createTestFlight();
//        long id = flight.getId();
//
//        mockMvc.perform(
//                get("/api/flights/{id}", id)
//                        .content(objectMapper.writeValueAsString(flight))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNumber());
//    }
//
//    @Test
//    public void whenGetFlightByFromToDate_thenStatus200() throws Exception {
//        Flight flight = createTestFlight();
//        String from = "Moscow";
//        String to = "Kiev";
//        String date = "2022-08-09";
//
//        mockMvc.perform(
//                get("/api/flights/{from}/{to}/{date}", from, to, date)
//                        .content(objectMapper.writeValueAsString(flight))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(flight.getId()))
//                .andExpect(jsonPath("$[0].from.city").value("Moscow")) //changed "from.name" to "from.city"
//                .andExpect(jsonPath("$[0].to.city").value("Kiev")); //changed "to.name" to "to.city"
//
//    }
//
//    // Seat Category
//    @Test
//    public void whenGetAllFreeSeatsOnFlight_thenStatus200() throws Exception {
//        Flight flight = createTestFlight();
//
//        mockMvc.perform(
//                get("/api/flights/allFree")
//                        .content(objectMapper.writeValueAsString(flight))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].isRegistered").value(false))
//                .andExpect(jsonPath("$[0].isSold").value(false));
//    }
//
//    // Seat Category
//    @Test
//    public void whenGetAllFreeSeatsOnFlightByEconomy_thenStatus200() throws Exception {
//        Flight flight = createTestFlight();
//
//        mockMvc.perform(
//                        get("/api/flights/allFreeEconomy")
//                                .content(objectMapper.writeValueAsString(flight))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].category").value(Category.ECONOMY.name())) //added ".name()"
//                .andExpect(jsonPath("$[0].isRegistered").value(false))
//                .andExpect(jsonPath("$[0].isSold").value(false));
//    }
//
//    // Seat Category
//    @Test
//    public void whenGetAllFreeSeatsOnFlightByBusiness_thenStatus200() throws Exception {
//        Flight flight = createTestFlight();
//
//        mockMvc.perform(
//                        get("/api/flights/allFreeBusiness")
//                                .content(objectMapper.writeValueAsString(flight))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].category").value(Category.BUSINESS.name())) // added ".name()"
//                .andExpect(jsonPath("$[0].isRegistered").value(false))
//                .andExpect(jsonPath("$[0].isSold").value(false));
//    }
//}
//
//
