package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chathandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.DEFAULT_ADVICE;
import static ru.bobcody.data.services.manual.TelegramMessageData.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FuckingGreatAdvicerHandlerTest extends AbstractSpringBootStarterTest {
    private final MainHandlerTextMessage mainHandlerTextMessage;

    private static final List<String> COMMANDS = PropertiesUtils.getCommandsByKey("fga.command");

    @DisplayName("get great advice!")
    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String inputCommand) {

        TELEGRAM_MESSAGE_1.setText(inputCommand);
        assertThat("@" + TELEGRAM_MESSAGE_1.getFrom().getUserName() + ", " + DEFAULT_ADVICE)
                .isEqualTo(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText());

        TELEGRAM_MESSAGE_2.setText(inputCommand);
        assertThat("@" + TELEGRAM_MESSAGE_2.getFrom().getUserName() + ", " + DEFAULT_ADVICE)
                .isEqualTo(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText());

        TELEGRAM_MESSAGE_3.setText(inputCommand);
        assertThat("@" + TELEGRAM_MESSAGE_3.getFrom().getUserName() + ", " + DEFAULT_ADVICE)
                .isEqualTo(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText());
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}