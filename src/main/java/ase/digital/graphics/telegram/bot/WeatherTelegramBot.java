package ase.digital.graphics.telegram.bot;

import ase.digital.graphics.telegram.service.TelegramBotService;
import lombok.Setter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Setter
public class WeatherTelegramBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;
    private TelegramBotService telegramBotService;

    public  WeatherTelegramBot(DefaultBotOptions botOptions, TelegramBotService telegramBotService) {
        super(botOptions);
        this.telegramBotService =telegramBotService;
    }
    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
       return  telegramBotService.sendAse(update);
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
