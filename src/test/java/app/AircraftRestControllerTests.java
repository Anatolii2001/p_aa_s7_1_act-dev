//package app;
//
//import app.controllers.AircraftRestController;
//import app.entities.Aircraft;
//import app.repositories.*;
//import app.services.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.*;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc(addFilters = false)
//public class AircraftRestControllerTests {
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private AircraftService aircraftService;
//    @Autowired
//    private AircraftRepository aircraftRepository;
//    @Autowired
//    private FlightRepository flightRepository;
//    @Autowired
//    private DestinationRepository destinationRepository;
//
//
//    @BeforeEach
//    public void resetDB() {
//        flightRepository.deleteAll();
//        destinationRepository.deleteAll();
//        aircraftRepository.deleteAll();
//    }
//
//
//    /**
//     * Aircraft creation test
//     * should return 201
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenCreateAircraft_thenStatus201() throws Exception {
//
//        Aircraft aircraft = createTestAircraft();
//
//        mockMvc.perform(
//                        post("/api/aircraft")
//                                .content(objectMapper.writeValueAsString(aircraft))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.brand").value("Boeing"));
//
//
//    }
//
//
//    /**
//     * Get Aircraft test
//     * should return 200
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenGetAircraft_thenStatus200() throws Exception {
//
//        Aircraft aircraft = createTestAircraft();
//        long id = aircraft.getId();
//
//        mockMvc.perform(
//                        get("/api/aircraft/{id}", id)
//                                .content(objectMapper.writeValueAsString(aircraft))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.brand").value("Boeing"));
//
//    }
//
//    /**
//     * Aircraft update test
//     * should return code 200
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenUpdateAircraft_thenStatus200() throws Exception {
//
//        Aircraft aircraft = createTestAircraft();
//        String newName = "testName";
//        aircraft.setName(newName);
//        aircraftService.save(aircraft);
//        mockMvc.perform(
//                        put("/api/aircraft/")
//                                .content(objectMapper.writeValueAsString(aircraftService.findAircraftByName("testName")))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.name").value("testName"));
//
//    }
//
//    /**
//     * Aircraft delete test
//     * should return 200
//     *
//     * @see AircraftRestController
//     */
//    @Test
//    public void whenDeleteAircraft_thenStatus200() throws Exception {
//
//        Aircraft aircraft = createTestAircraft();
//        long id = aircraft.getId();
//
//        mockMvc.perform(
//                        delete("/api/aircraft/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(aircraft)));
//
//    }
//
//
//    /**
//     * A helper method for creating an Aircraft.
//     *
//     */
//    private Aircraft createTestAircraft() {
//        Aircraft aircraft = new Fleet().createBoeing737();
//        aircraftService.save(aircraft);
//        return aircraft;
//    }
//
//
//}
