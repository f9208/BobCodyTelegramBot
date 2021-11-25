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
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

class OneTwoThreeHandlerTest extends AbstractHandlerTest {
    OneTwoThreeHandler oneTwoThreeHandler = new OneTwoThreeHandler();
    List<String> commands = getCommandsByKey("onetwothree.command");

    @BeforeEach
    void init() {
        oneTwoThreeHandler.setCommands(commands);
        oneTwoThreeHandler.setPhrases(getPropertiesByPath("answers/onetwothree.properties", "\uFEFFonetwothree.phrases"));
        mainHandlerTextMessage = new MainHandlerTextMessage(Lists.list(oneTwoThreeHandler));
    }

    @DisplayName("!123")
    @ParameterizedTest
    @ValueSource(strings = {"123", "!123", "!одиндватри"})
    void handle(String inputText) {
        message.setText(inputText);
        SendMessage sendMessage = mainHandlerTextMessage.handle(message);
        assertThat(sendMessage.getText(), is(in(answer())));
    }

    List<String> answer() {
        return getPropertiesByPath("answers/onetwothree.properties", "\uFEFFonetwothree.phrases");
    }
}