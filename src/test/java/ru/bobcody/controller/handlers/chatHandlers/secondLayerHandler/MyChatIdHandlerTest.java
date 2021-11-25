package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.bobcody.controller.handlers.chatHandlers.AbstractHandlerTest;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MyChatIdHandlerTest extends AbstractHandlerTest {
    MyChatIdHandler myChatIdHandler = new MyChatIdHandler();
    List<String> commands = getCommandsByKey("id.command");

    @BeforeEach
    void init() {
        myChatIdHandler.setCommands(commands);
        mainHandlerTextMessage = new MainHandlerTextMessage(Lists.list(myChatIdHandler));
    }

    @DisplayName("get chat Id")
    @ParameterizedTest
    @ValueSource(strings = {"!id", "!айди"})
    void handle(String id) {
        message.setText(id);
        SendMessage sendMessage = mainHandlerTextMessage.handle(message);
        assertThat(sendMessage.getText(), is("айдишник этого чата:  " + message.getChatId()));
    }
}