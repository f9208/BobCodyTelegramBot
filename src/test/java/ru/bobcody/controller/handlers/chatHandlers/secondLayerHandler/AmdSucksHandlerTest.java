package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.AmdSucksHandler;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_3;

public class AmdSucksHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    AmdSucksHandler amdSucksHandler;
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    private static final List<String> COMMANDS = PropertiesUtils.getCommandsByKey("amd.command");

    @DisplayName("AmdSucksOrNot?")
    @ParameterizedTest
    @MethodSource("getPhrases")
    public void amdHasAnswer(String inputText) {
        TELEGRAM_MESSAGE_3.setText(inputText);
        System.out.println(inputText);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText();
        System.out.println(answer);
        String[] replay = new String[]{"@" + TELEGRAM_MESSAGE_3.getFrom().getUserName() + ", AMD форева!",
                "@" + TELEGRAM_MESSAGE_3.getFrom().getUserName() + ", AMD сосет"};
        assertThat(replay).contains(answer);
    }

    @DisplayName("amd_string_not_continued?")
    @ParameterizedTest
    @MethodSource("getWrongPhrases")
    public void noAmd(String inputText) {
        TELEGRAM_MESSAGE_3.setText(inputText);
        System.out.println(inputText);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_3).getText();
        assertThat(answer).isNull();
    }

    private static List<String> getPhrases() {
        List<String> results = new ArrayList<>();
        for (String oneCommands : COMMANDS) {
            results.add(oneCommands);
            results.add("в середине слова " + oneCommands + " и еще слова");
            results.add("в конце слова " + oneCommands);
            results.add("в конце слова  со знаком препинания " + oneCommands + ".");
            results.add(oneCommands + ", в начале слова со знаком препинания");
            results.add(oneCommands + "! в начале слова со знаком препинания");
        }
        return results;
    }

    static private List<String> getWrongPhrases() {
        List<String> results = new ArrayList<>();
        for (String oneCommands : COMMANDS) {
            results.add("в AMDа слова" + oneCommands + " и еще слова");
            results.add("в концАМДе слова" + oneCommands);
            results.add("в конце wef" + oneCommands + ".");
            results.add(oneCommands + "w, в начале ");
            results.add(oneCommands + "fwe! препинания");
        }
        return results;
    }

}
