package app;

import app.controllers.TicketRESTController;
import app.entities.*;
import app.services.TicketService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Class for unit test for {@link app.controllers.TicketRESTController}
 * with MockMVC
 *
 * @author Anastasia Budaeva
 */
@WebMvcTest(value = TicketRESTController.class)
@MockBean(LoginSuccessHandler.class)
@MockBean(UserService.class)
public class TicketRESTControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    private final Ticket ticket_1 = new Ticket(1L,
            new Passenger(1L, "Harry", "Potter", "email@mail.ru", 11, 12),
            new Flight(), new Seat(), Ticket.Subcategory.ECONOMY_CLASS ,"RDGT456Y");
    private final Ticket ticket_2 = new Ticket(1L, new Passenger(), new Flight(),
            new Seat(), Ticket.Subcategory.BUSINESS_CLASS ,"GGTT456Y");

    @Test
    void createNewTicket_NewTicket_Returns200() throws Exception {
        mockMvc.perform(post("/api/tickets/")
                        .content(objectMapper.writeValueAsString(ticket_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        verify(ticketService, times(1)).saveTicket(ticket_1);
    }
    @Test
    void getTicketById_correctID_Returns200andTicket() throws Exception {
        final long id = 1L;
        when(ticketService.getTicket(id)).thenReturn(ticket_1);

        mockMvc.perform(get("/api/tickets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ticket_1)))
                .andDo(print());
        verify(ticketService, times(1)).getTicket(id);
    }

    @Test
    void getTicketById_invalidID_Returns404() throws Exception {
        final long id = 1L;
        when(ticketService.getTicket(id)).thenReturn(null);

        mockMvc.perform(get("/api/tickets/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(ticketService, times(1)).getTicket(id);
    }

    @Test
    void getListTickets_Returns200andListOfTickets() throws Exception {
        final List<Ticket> ticketList = Arrays.asList(ticket_1, ticket_2);
        when(ticketService.getAllTickets()).thenReturn(ticketList);

        mockMvc.perform(get("/api/tickets/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ticketList)))
                .andDo(print());
        verify(ticketService, times(1)).getAllTickets();
    }

    @Test
    void getListTickets_Returns404() throws Exception {
        when(ticketService.getAllTickets()).thenReturn(null);

        mockMvc.perform(get("/api/tickets/"))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(ticketService, times(1)).getAllTickets();
    }

    @Test
    void deleteTicketById_CorrectID_Returns200() throws Exception {
        final long id = 1L;
        when(ticketService.getTicket(id)).thenReturn(ticket_2);

        mockMvc.perform(delete("/api/tickets/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        verify(ticketService, times(1)).deleteTicket(id);
    }

    @Test
    void deleteTicketById_InvalidID_Returns304() throws Exception {
        final long id = 1L;
        when(ticketService.getTicket(id)).thenReturn(null);

        mockMvc.perform(delete("/api/tickets/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotModified())
                .andDo(print());
        verify(ticketService, times(0)).deleteTicket(id);
    }

    @Test
    void deleteAllTickets_Returns200() throws Exception {
        mockMvc.perform(delete("/api/tickets/")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        verify(ticketService, times(1)).deleteAllTickets();
    }

    @Test
    void editTicket_Ticket_Returns200() throws Exception {
        mockMvc.perform(put("/api/tickets/")
                        .content(objectMapper.writeValueAsString(ticket_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        verify(ticketService, times(1)).editTicket(ticket_1);
    }

    @Test
    void getAllTicketsByFlightId_correctID_Returns200andListOfTickets() throws Exception {
        final List<Ticket> ticketList = Arrays.asList(ticket_1, ticket_2);
        final long id = 3L;
        when(ticketService.findAllTicketsByFlightId(id)).thenReturn(ticketList);

        mockMvc.perform(get("/api/tickets/flight/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ticketList)))
                .andDo(print());
        verify(ticketService, times(1)).findAllTicketsByFlightId(id);
    }

    @Test
    void getAllTicketsByFlightId_InvalidID_Returns404andListOfTickets() throws Exception {
        final long id = 3L;
        when(ticketService.findAllTicketsByFlightId(id)).thenReturn(null);

        mockMvc.perform(get("/api/tickets/flight/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(ticketService, times(1)).findAllTicketsByFlightId(id);
    }

}
