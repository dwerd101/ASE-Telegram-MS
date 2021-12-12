package ase.digital.graphics.telegram.controller;

import ase.digital.graphics.telegram.bot.WeatherTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BotController {
    private final WeatherTelegramBot telegramBot;
    @PostMapping("/")
    @SneakyThrows
    public BotApiMethod<?> getWeather(@RequestBody Update update) {
     return telegramBot.onWebhookUpdateReceived(update);
    }
}
