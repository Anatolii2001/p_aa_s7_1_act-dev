package app.events;

import app.entities.Flight;
import app.entities.Ticket;
import app.services.EmailService;
import app.services.FlightService;
import app.services.TelegramMessageService;
import app.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Flight Event Listener
 *
 * Listens for events published by the Flight Event Publisher
 *
 * Handles events related to flight status change, aircraft replacement,
 * remaining time before department etc.
 *
 * @author Alexander Plekhov
 */

@Component
@AllArgsConstructor
public class FlightEventListener {

    private final EmailService emailService;
    private final TicketService ticketService;
    private final FlightService flightService;
//    private final TelegramMessageService telegramMessageService;

    /**
     * getting all tickets on flight -> sending a flight status change message
     */
    @Async
    @EventListener
    public void handleFlightEvent(FlightStatusChangeEvent changeEvent) {
        List<Ticket> ticketList = ticketService.findAllTicketsByFlightId(changeEvent.getFlight().getId());
        String status = changeEvent.getFlight().getFlightStatus().toString();

        StringBuilder statusChangeMessage = new StringBuilder();
        statusChangeMessage.append("Текущий статус рейса: ");
        statusChangeMessage.append(status);

        if (status.equals("Регистрация")) {
            statusChangeMessage.append(" Регистрация заканчивается за 40 минут до вылета.");
        }
        if (status.equals("Посадка")) {
            statusChangeMessage.append(" Длительность посадки — 20 минут.");
        }
        if (status.equals("В пути")) {
            statusChangeMessage.append(" Приятного полёта!");
        }

        for (Ticket ticket : ticketList) {
            emailService.sendFlightEventEmail(ticket.getPassenger().getEmail(), "Статус рейса", statusChangeMessage.toString());
            telegramMessageService.sendFlightEventMessage(ticket.getPassenger().getTelegram(), statusChangeMessage.toString());
        }
    }

    /**
     * getting all tickets on flight -> sending an aircraft replacement message
     */
    @Async
    @EventListener
    public void handleFlightEvent(FlightAircraftReplacementEvent replacementEvent) {
        List<Ticket> ticketList = ticketService.findAllTicketsByFlightId(replacementEvent.getFlight().getId());

        StringBuilder aircraftReplaceMessage = new StringBuilder();
        aircraftReplaceMessage.append("На рейс назначен новый самолёт: ");
        aircraftReplaceMessage.append(replacementEvent.getFlight().getAircraft().getBrand());
        aircraftReplaceMessage.append(" ");
        aircraftReplaceMessage.append(replacementEvent.getFlight().getAircraft().getModel());

        for (Ticket ticket : ticketList) {
            emailService.sendFlightEventEmail(ticket.getPassenger().getEmail(), "Замена самолёта", aircraftReplaceMessage.toString());
            telegramMessageService.sendFlightEventMessage(ticket.getPassenger().getTelegram(), aircraftReplaceMessage.toString());
        }
    }

    @Async
    @EventListener
    public void handleFlightEvent(RemindAboutFlightEvent remindAboutFlightEvent) {
        List<Integer> listHours = Arrays.asList(1, 3, 24);

        for (Integer i : listHours) {
        List<Flight> flightList = flightService.findFlightByDepartureDateTime(remindAboutFlightEvent.getCurrentDateTime().plusHours(i));
            if (flightList != null) {
                for (Flight flight : flightList) {
                    List<Ticket> ticketList = ticketService.findAllTicketsByFlightId(flight.getId());
                    for (Ticket ticket : ticketList) {
                        String headerFromTo = "Рейс " + ticket.getFlight().getFrom().getCity() + "-" + ticket.getFlight().getTo().getCity();
                        String message = buildingMessageAboutFlight(flight);

                        emailService.sendFlightEventEmail(ticket.getPassenger().getEmail(), headerFromTo, message);
                        telegramMessageService.sendFlightEventMessage(ticket.getPassenger().getTelegram(), message);
                    }
                }
            }
        }
    }

    /**
     * method for generating a message with flight information
     */
    private String buildingMessageAboutFlight(Flight flight) {
        LocalDateTime departDateTime = flight.getDepartureDateTime();
        LocalDateTime now = LocalDateTime.now().withNano(0);
        long diffHours = ChronoUnit.HOURS.between(now, departDateTime);

        return "Рейс " + flight.getFrom().getCity() + " - "
                                    + flight.getTo().getCity()
                + " отправляется в "
                                    + departDateTime.getHour() + "ч. "
                                    + departDateTime.getMinute() + "м. по Московскому времени."
                + "\nДата отправления: "
                                    + departDateTime.getDayOfMonth() + " "
                                    + departDateTime.getMonth().name() + " "
                                    + departDateTime.getYear() + " г., "
                                    + departDateTime.getDayOfWeek().toString()
                + "\nДо рейса осталось: "
                                    + diffHours + "ч."
                + "\nТекущий статус рейса: "
                                    + flight.getFlightStatus().toString()
                + "\nСамолёт: "
                                    + flight.getAircraft().getBrand() + " "
                                    + flight.getAircraft().getModel();
    }
}
