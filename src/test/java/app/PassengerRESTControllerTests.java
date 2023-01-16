package app;

import app.controllers.PassengerRESTController;
import app.entities.Passenger;
import app.services.PassengerService;
import app.services.UserService;
import app.util.LoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Class for unit test {@link app.controllers.PassengerRESTController}
 * with MockMVC
 *
 * @author Anastasia Budaeva
 */
@WebMvcTest(value = PassengerRESTController.class)
@MockBean(LoginSuccessHandler.class)
@MockBean(UserService.class)
public class PassengerRESTControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PassengerService passengerService;

    private final Passenger passenger_1 = new Passenger(1L, "Harry", "Potter", "email@mail.ru", 11, 12, 1);
    private final Passenger passenger_2 = new Passenger(2L, "Green", "Apelsin", "email@mail.ru", 11, 12, 1);
    private final Passenger passenger_3 = new Passenger(3L, "Party", "Monster", "monster@mail.ru", 11, 12, 1);

    @Test
    void getAllPassengers_Returns200andListOfPassengers() throws Exception {
        final List<Passenger> passengerList = Arrays.asList(passenger_1, passenger_2, passenger_3);
        when(passengerService.getAllPassenger()).thenReturn(passengerList);

        mockMvc.perform(get("/api/passenger/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passengerList)))
                .andDo(print());
        verify(passengerService, times(1)).getAllPassenger();
    }

    @Test
    void getPassenger_correctID_thenReturns200andPassenger() throws Exception {
        final long id = 1L;
        when(passengerService.getPassenger(id)).thenReturn(passenger_1);

        mockMvc.perform(get("/api/passenger/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passenger_1)))
                .andDo(print());
        verify(passengerService, times(1)).getPassenger(id);
    }

    @Test
    void getPassenger_invalidID_thenReturns404() throws Exception {
        final long id = 2L;
        when(passengerService.getPassenger(id)).thenReturn(null);

        mockMvc.perform(get("/api/passenger/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(passengerService, times(1)).getPassenger(id);
    }

    @Test
    void addNewPassenger_newPassenger_Returns200andPassenger() throws Exception {
        mockMvc.perform(post("/api/passenger/")
                        .content(objectMapper.writeValueAsString(passenger_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) //чтобы Security не мешал
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passenger_1)))
                .andDo(print());
        verify(passengerService, times(1)).savePassenger(passenger_1);
    }

    @Test
    void editPassenger_Passenger_Returns200andPassenger() throws Exception {
        when(passengerService.update(passenger_1)).thenReturn(passenger_1);

        mockMvc.perform(put("/api/passenger/")
                        .content(objectMapper.writeValueAsString(passenger_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passenger_1)))
                .andDo(print());
        verify(passengerService, times(1)).update((passenger_1));
    }

    @Test
    void deletePassenger_correctID_Returns200() throws Exception {
        long id = 3L;
        when(passengerService.getPassenger(id)).thenReturn(passenger_3);

        mockMvc.perform(delete("/api/passenger/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
        verify(passengerService, times(1)).getPassenger(id);
        verify(passengerService, times(1)).deletePassenger(id);
    }

    @Test
    void deletePassenger_InvalidID_Returns304() throws Exception {
        long id = 3L;
        when(passengerService.getPassenger(id)).thenReturn(null);

        mockMvc.perform(delete("/api/passenger/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotModified())
                .andDo(print());
        verify(passengerService, times(1)).getPassenger(id);
    }

}


