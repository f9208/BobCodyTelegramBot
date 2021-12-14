package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.TodayIsHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.services.data.TelegramMessageData.TELEGRAM_MESSAGE_3;

class TodayIsHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    TodayIsHandler todayIsHandler;
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    private static final List<String> COMMANDS = PropertiesUtils.getCommandsByKey("today.is.command");

    @DisplayName("get today")
    @ParameterizedTest
    @MethodSource("getRightCommands")
    void handle(String command) {
        TELEGRAM_MESSAGE_3.setText(command);
        StringBuilder expectedAnswer = new StringBuilder("Сегодня ")
                .append(LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL,
                        new Locale("ru", "RU")))
                .append(", ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        .withLocale(new Locale("ru", "RU"))))
                .append("\n")
                .append("время в Ижевске: ")
                .append(LocalDateTime.now(ZoneId.of("GMT+4")).format(DateTimeFormatter.ofPattern("HH:mm")));
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText()).
                startsWith(expectedAnswer.toString());
    }

    @DisplayName("get wrong today")
    @ParameterizedTest
    @MethodSource("getWrongCommands")
    void noHandle(String command) {
        TELEGRAM_MESSAGE_3.setText(command);
        Assertions.assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText()).isNull();
    }

    private static List<String> getRightCommands() {
        return COMMANDS;
    }

    private static List<String> getWrongCommands() {
        return Lists.list("врея ", "тайм", "сколька время", "прослушайте голосовое сообщение");
    }
}