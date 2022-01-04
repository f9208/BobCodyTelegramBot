package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils.getCommandsByKey;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_2;

class FridayHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    private final static List<String> COMMANDS = getCommandsByKey("friday.command");

    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String inputMessage) {
        TELEGRAM_MESSAGE_2.setText(inputMessage);
        String[] exp = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText().split("[\\s+\\.!]");
       assertThat(answers()).containsAnyOf(exp);

    }

    private List<String> answers() {
        List<String> results = new ArrayList<>();
        DayOfWeek d = DayOfWeek.MONDAY;
        for (int i = 0; i < 7; i++) {
            results.add(d.getDisplayName(TextStyle.FULL,
                    new Locale("ru", "RU")));
            d = d.plus(1);
        }
        return results;
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}