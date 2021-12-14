package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.CourseHandlerI;
import ru.bobcody.services.data.TelegramMessageData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CourseHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    CourseHandlerI courseHandler;
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    private static final List<String> commands = PropertiesUtils.getCommandsByKey("course.command");

    @DisplayName("get course")
    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String command) {
        Message inputMessage = TelegramMessageData.TELEGRAM_MESSAGE_4;
        inputMessage.setText(command);
        assertThat(mainHandlerTextMessage.handle(inputMessage).getText()).startsWith("текущий курс валют по курсу ЦБ РФ на");
    }

    public static List<String> getCommands() {
        return commands;
    }
}