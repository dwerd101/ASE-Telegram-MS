package ase.digital.graphics.telegram.bot.config;

import ase.digital.graphics.telegram.bot.WeatherTelegramBot;
import ase.digital.graphics.telegram.service.TelegramBotService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.userName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;

    @Bean
    public WeatherTelegramBot myWizardTelegramBot(TelegramBotService telegramBotService) {
        DefaultBotOptions options = new DefaultBotOptions();
        WeatherTelegramBot mySuperTelegramBot = new WeatherTelegramBot(options, telegramBotService);
        mySuperTelegramBot.setBotUserName(botUserName);
        mySuperTelegramBot.setBotToken(botToken);
        mySuperTelegramBot.setWebHookPath(webHookPath);
        return mySuperTelegramBot;
    }
}