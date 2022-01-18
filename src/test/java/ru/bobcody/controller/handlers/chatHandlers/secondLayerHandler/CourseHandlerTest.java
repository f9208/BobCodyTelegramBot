package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chathandlers.PropertiesUtils;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.CourseHandlerI;
import ru.bobcody.data.services.manual.TelegramMessageData;

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