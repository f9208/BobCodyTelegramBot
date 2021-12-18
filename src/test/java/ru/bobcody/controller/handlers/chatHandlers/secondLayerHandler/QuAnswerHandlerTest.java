package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.updates.handlers.chatHandlers.MainHandlerTextMessage;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils.getCommandsByKey;
import static ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils.getPropertiesByPath;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_2;

class QuAnswerHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    private final static List<String> COMMANDS = getCommandsByKey("qu.command");
    private List<String> phrases =
            getPropertiesByPath("answers/touchBot.properties",
                    "qu.answer");

    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String inputText) {
        TELEGRAM_MESSAGE_2.setText(inputText);
        String actual = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText();
        List<String> phrasesWithName = phrases.stream()
                .map((p) -> "@" + TELEGRAM_MESSAGE_2.getFrom().getUserName() + ", " + p).collect(Collectors.toList());
        assertThat(phrasesWithName).contains(actual);
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}