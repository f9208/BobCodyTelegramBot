package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chathandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.TelegramMessageData.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class HelpReplayHandlerTest extends AbstractSpringBootStarterTest {
    private final MainHandlerTextMessage mainHandlerTextMessage;
    private final String help = PropertiesUtils.getAnyProperties("answers/help.properties", "\uFEFFprint.help");
    private static final List<String> COMMANDS = PropertiesUtils.getCommandsByKey("help.command");

    @DisplayName("get help")
    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String invokeText) {
        TELEGRAM_MESSAGE_1.setText(invokeText);
        TELEGRAM_MESSAGE_2.setText(invokeText);
        TELEGRAM_MESSAGE_3.setText(invokeText);
        //дада, я сравниваю тест, взятый из одного и того же файла между собой.
//        System.out.println(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText());
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText()).isEqualTo(help);
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText()).isEqualTo(help);
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText()).isEqualTo(help);
    }

    @Test
    void noHandle() {
        TELEGRAM_MESSAGE_3.setText("какой то беспомощный хелп");
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText()).isNull();
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}