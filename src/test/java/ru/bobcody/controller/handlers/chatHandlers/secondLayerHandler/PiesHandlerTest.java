package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.PiesHandler;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils.getCommandsByKey;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_1;

class PiesHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    PiesHandler piesHandler;
    @Autowired
    DataSource dataSource;
    private final static List<String> COMMANDS = getCommandsByKey("pies.command");

    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String inputText) throws IOException {
        TELEGRAM_MESSAGE_1.setText(inputText);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("пирожок пирожок ура\nurl://somwere.com");
    }

    @Test
    void ignore() {
        TELEGRAM_MESSAGE_1.setText("fweofpk");
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText()).isNull();
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}