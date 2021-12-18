package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.FuckingGreatAdviceHandler;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.utils.Adviser;
import ru.bobcody.thirdPartyAPI.fuckingGreatAdvice.FuckingGreatAdviser;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.bobcody.data.services.manual.TelegramMessageData.*;

class FuckingGreatAdvicerHandlerTest extends AbstractSpringBootStarterTest {
    @Mock //эдвайсер работает с внешним апи которое может в любой момент отвалиться поэтому мок
    FuckingGreatAdviser fuckingGreatAdviser = mock(FuckingGreatAdviser.class);
    @Autowired
    FuckingGreatAdviceHandler fuckingGreatAdviceHandler;
    @Autowired
    Adviser adviser;
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    private static final List<String> COMMANDS = PropertiesUtils.getCommandsByKey("fga.command");

    @BeforeEach
    void init() throws IOException {
        when(fuckingGreatAdviser.getAdvice()).thenReturn("советую не обкакаться!");
        adviser.setFuckingGreatAdvicer(fuckingGreatAdviser);
    }

    @DisplayName("get great advice!")
    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String inputCommand) {

        TELEGRAM_MESSAGE_1.setText(inputCommand);
        assertThat("@" + TELEGRAM_MESSAGE_1.getFrom().getUserName() + ", советую не обкакаться!")
                .isEqualTo(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText());

        TELEGRAM_MESSAGE_2.setText(inputCommand);
        assertThat("@" + TELEGRAM_MESSAGE_2.getFrom().getUserName() + ", советую не обкакаться!")
                .isEqualTo(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText());

        TELEGRAM_MESSAGE_3.setText(inputCommand);
        assertThat("@" + TELEGRAM_MESSAGE_3.getFrom().getUserName() + ", советую не обкакаться!")
                .isEqualTo(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText());
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}