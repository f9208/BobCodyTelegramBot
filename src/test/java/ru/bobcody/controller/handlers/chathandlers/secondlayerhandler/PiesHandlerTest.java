package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.updates.handlers.chathandlers.MainHandlerTextMessage;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.controller.handlers.chathandlers.PropertiesUtils.getCommandsByKey;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_1;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PiesHandlerTest extends AbstractSpringBootStarterTest {
    private final static List<String> COMMANDS = getCommandsByKey("pies.command");

    @Autowired
    private final MainHandlerTextMessage mainHandlerTextMessage;

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