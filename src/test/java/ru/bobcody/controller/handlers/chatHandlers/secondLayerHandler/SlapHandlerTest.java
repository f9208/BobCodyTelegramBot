package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.bobcody.controller.handlers.chathandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_3;

class SlapHandlerTest extends AbstractSpringBootStarterTest {
    List<String> answers = PropertiesUtils.getPropertiesByPath("answers/touchBot.properties", "slap.phrases");
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    private static final List<String> COMMANDS = PropertiesUtils.getCommandsByKey("slap.command");

    @DisplayName("slap")
    @ParameterizedTest
    @MethodSource("getRightCommands")
    void handle(String inputText) {
        TELEGRAM_MESSAGE_3.setText(inputText);
        SendMessage answerMessage = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3);
        String outputText = answerMessage.getText();
        assertThat(outputText).
                startsWith("@" + TELEGRAM_MESSAGE_3.getFrom().getUserName() + ", ");
        String index = ("@" + TELEGRAM_MESSAGE_3.getFrom().getUserName() + ", ");
        String cut = outputText.substring(index.length());
        assertThat(answers).contains(cut);
    }

    @DisplayName("slap")
    @ParameterizedTest
    @MethodSource("getWrongCommands")
    void noHandle(String inputText) {
        TELEGRAM_MESSAGE_3.setText(inputText);
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText()).isNull();
    }

    private static List<String> getRightCommands() {
        List<String> phrases = new ArrayList<>();
        for (String oneCommand : COMMANDS) {
            phrases.add("слап в конце предложения без точки " + oneCommand);
            phrases.add(oneCommand + " слап в начале предложения ");
            phrases.add("slap в середине " + oneCommand + " предложения ");
            phrases.add("слап в конце предложения с точкой " + oneCommand + ".");
            phrases.add(oneCommand + ". слап в начале предложения с точкой");
            phrases.add("slap в середине " + oneCommand + ". предложения с точкой");
            phrases.add("слап в конце предложения с точкой " + oneCommand + "!");
            phrases.add(oneCommand + "? слап в начале предложения со знаком");
            phrases.add("slap в середине " + oneCommand + "%%№ предложения со знаком");
        }
        return phrases;
    }

    private static List<String> getWrongCommands() {
        List<String> phrases = new ArrayList<>();
        phrases.add("ботяра ковыряла");
        phrases.add("ботботинок фцшуа ковыряла");
        phrases.add("ачо ботунедали шо ковыряла");
        phrases.add("bobuu ковыряла");
        phrases.add("боb");
        phrases.add("ботяра boob");
        phrases.add("ботяра cody");
        phrases.add("ботяра послапай по голове");
        return phrases;
    }
}