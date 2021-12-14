package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chatHandlers.MainHandlerTextMessage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.services.data.TelegramMessageData.TELEGRAM_MESSAGE_1;

class MyChatIdHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;

    private static final List<String> COMMANDS = PropertiesUtils.getCommandsByKey("id.command");

    @DisplayName("get chat Id")
    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String command) {
        TELEGRAM_MESSAGE_1.setText(command);
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText())
                .isEqualTo("айдишник этого чата:  " + TELEGRAM_MESSAGE_1.getChatId());
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}