package app.services;


/** интерфейс для расылки писем
 * @author Minibaeva Elvira
 */
public interface EmailService {

    void sendFlightEventEmail(String email, String subject, String message);

}
