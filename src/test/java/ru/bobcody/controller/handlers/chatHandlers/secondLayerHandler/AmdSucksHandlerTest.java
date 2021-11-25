package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
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

public class AmdSucksHandlerTest extends AbstractHandlerTest {
    AmdSucksHandler amdSucksHandler = new AmdSucksHandler();
    List<String> commands = getCommandsByKey("amd.command");

    @BeforeEach
    void init() {
        amdSucksHandler.setCommands(commands);
        mainHandlerTextMessage = new MainHandlerTextMessage(Lists.list(amdSucksHandler));
    }

    @DisplayName("AmdSucksOrNot?")
    @ParameterizedTest
    @ValueSource(strings = {"amd",
            "fioff амд ffwef",
            "амд.Но! я имею сказать",
            "wawif amd, iwfiw3I/ afoijwafj ifj",
            "fiwjf цуаощш оца амд, шцфощаоца амд.",
            "шацшуоа iiwf amd. fejafojewa"})
    public void amdHasAnswer(String inputText) {
        message.setText(inputText);
        SendMessage sendMessage = mainHandlerTextMessage.handle(message);
        assertThat(sendMessage.getText(), is(in(new String[]{"@" + user.getUserName() + ", AMD сосет",
                "@" + user.getUserName() + ", AMD форева!"})));
    }

    @DisplayName("amd_string_not_continued?")
    @ParameterizedTest
    @ValueSource(strings = {"amdf",
            "fioff aамдf ffwef",
            "амдц.f",
            "wawif аmd, iwfiw3I/ afoijwafj ifj",
            "fiwjf цуаощш оца амдf, шцфощаоца",
            "шацшуоа iiwf amdfejafojewa"})
    public void amdNoIgnored(String inputText) {
        message.setText(inputText);
        SendMessage sendMessage = mainHandlerTextMessage.handle(message);
        assertThat(sendMessage.getText(), is(Matchers.nullValue()));
    }
}
