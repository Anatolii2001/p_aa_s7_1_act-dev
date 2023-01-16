//package app;
//
//import app.entities.Destination;
//import app.services.DestinationService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Class for integration tests
// */
//
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//public class DestinationRestControllerMockMVCIntegrationTest {
//        @Autowired
//        private ObjectMapper objectMapper;
//        @Autowired
//        DestinationService destinationService ;
//        @Autowired
//        private MockMvc mockMvc;
//
//        /** Create addDestination method for some tests */
//        private Destination createTestDestination() {
//                Destination testdestination = new Destination("Paris", "France", "Europe");
//                return destinationService.saveDestination(testdestination);
//        }
//
//        /** Delete all Destinations after tests */
//        @AfterEach
//        public void resetDb() {
//            destinationService.deleteAllDestination();
//        }
//
//        /** Test for update Destination */
//        @Test
//        void give_Destination_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
//                createTestDestination();
//                mockMvc.perform(
//                                patch("/api/destination")
//                                        .content(objectMapper.writeValueAsString(new Destination(1L, "Berlin", "Germany",  "Europe")))
//                                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.id").value("1"))
//                        .andExpect(jsonPath("$.city").value("Berlin"))
//                        .andExpect(jsonPath("$.country_code").value("DE"))
//                        .andExpect(jsonPath("$.country_name").value("Germany"))
//                        .andExpect(jsonPath("$.continent").value("Europe"));
//        }
//
//        /** Test for add Destination */
//        @Test
//        void givenDestination_whenAdd_thenStatus201andDestinationReturned() throws Exception {
//            Destination testdestination = new Destination("Paris", "France", "Europe");
//            mockMvc.perform(
//                            post("/api/destination")
//                                    .content(objectMapper.writeValueAsString(testdestination))
//                                    .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isCreated())
//                    .andExpect(jsonPath("$.id").isNumber())
//                    .andExpect(jsonPath("$.city").value("Paris"))
//                    .andExpect(jsonPath("$.country_code").value("FR"))
//                    .andExpect(jsonPath("$.country_name").value("France"))
//                    .andExpect(jsonPath("$.continent").value("Europe"));
//        }
//
//        /** Test for get all Destinations */
//        @Test
//        void givenDestinations_whenGetDestinations_thenStatus200() throws Exception {
//                Destination d1 = createTestDestination();
//                Destination d2 = createTestDestination();
//                mockMvc.perform(
//                                get("/api/destination"))
//                        .andExpect(status().isOk())
//                        .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(d1, d2))));
//        }
//
//        /** Test for get Destination by id*/
//        @Test
//        void givenId_whenGetExistingDestination_thenStatus200andDestinationReturned() throws Exception {
//                Destination destination = createTestDestination();
//                Long id = destination.getId();
//
//                mockMvc.perform(
//                                get("/api/destination/{id}", id))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.id").isNumber())
//                        .andExpect(jsonPath("$.city").value("Paris"))
//                        .andExpect(jsonPath("$.country_code").value("FR"))
//                        .andExpect(jsonPath("$.country_name").value("France"))
//                        .andExpect(jsonPath("$.continent").value("Europe"));
//        }
//
//        /** Test for get Destination by county_name*/
//        @Test
//        void givenCountryName_whenGetExistingDestination_thenStatus200andDestinationReturned() throws Exception {
//                Destination destination = createTestDestination();
//                String country_name = destination.getCountry_name();
//
//                mockMvc.perform(
//                                get("/api/destination/country")
//                                        .param("country_name", country_name))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.id").isNumber())
//                        .andExpect(jsonPath("$.city").value("Paris"))
//                        .andExpect(jsonPath("$.country_code").value("FR"))
//                        .andExpect(jsonPath("$.country_name").value("France"))
//                        .andExpect(jsonPath("$.continent").value("Europe"));
//        }
//
//        /** Test for get Destination by city*/
//        @Test
//        void givenCity_whenGetExistingDestination_thenStatus200andDestinationReturned() throws Exception {
//                Destination destination = createTestDestination();
//                String city = destination.getCity();
//
//                mockMvc.perform(
//                                get("/api/destination/city")
//                                        .param("city", city))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.id").isNumber())
//                        .andExpect(jsonPath("$.city").value("Paris"))
//                        .andExpect(jsonPath("$.country_code").value("FR"))
//                        .andExpect(jsonPath("$.country_name").value("France"))
//                        .andExpect(jsonPath("$.continent").value("Europe"));
//        }
//
//
//}
