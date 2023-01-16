package app.services;

/**
 * TODO * Telegram Message Service
 */

public interface TelegramMessageService {

    void sendFlightEventMessage(long chatId, String message);

}
