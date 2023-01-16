package app.services;


import app.util.TgBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO * Telegram Message Service Impl
 */


@Service
public class TelegramMessageServiceImpl implements TelegramMessageService {

    @Autowired
    TgBot tgBot;

    @Override
    public void sendFlightEventMessage(long chatId, String message) {
        tgBot.sendingMessage(chatId, message);
    }
}
