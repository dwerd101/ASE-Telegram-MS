package ase.digital.graphics.telegram.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InlineKeyboardMarkupConfig {
    @Bean
    public InlineKeyboardMarkup inlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonMoscow = new InlineKeyboardButton();
        InlineKeyboardButton buttonSaintPetersburg = new InlineKeyboardButton();
        InlineKeyboardButton buttonYaroslavl = new InlineKeyboardButton();
        InlineKeyboardButton buttonYourCity = new InlineKeyboardButton();
        buttonMoscow.setText("Москва");
        buttonMoscow.setCallbackData("buttonMoscow");
        buttonSaintPetersburg.setText("Санкт-Петербург");
        buttonSaintPetersburg.setCallbackData("buttonPetersburg");
        buttonYaroslavl.setText("Ярославль");
        buttonYaroslavl.setCallbackData("buttonYaroslavl");
        buttonYourCity.setText("Ваш город");
        buttonYourCity.setCallbackData("buttonYourCity");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonMoscow);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonSaintPetersburg);
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(buttonYaroslavl);
        keyboardButtonsRow3.add(buttonYourCity);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
