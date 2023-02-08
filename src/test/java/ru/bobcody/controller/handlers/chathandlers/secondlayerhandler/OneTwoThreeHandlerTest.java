//package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static ru.bobcody.controller.handlers.chathandlers.PropertiesUtils.getCommandsByKey;
//import static ru.bobcody.controller.handlers.chathandlers.PropertiesUtils.getPropertiesByPath;
//import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_2;
//
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//class OneTwoThreeHandlerTest extends AbstractSpringBootStarterTest {
//    private static List<String> COMMANDS = getCommandsByKey("onetwothree.command");
//
//    private final TextMessageMainHandler textMessageMainHandler;
//    private final List<String> phrases =
//            getPropertiesByPath("src/test/oldRes/answers/onetwothree.properties",
//                    "\uFEFFonetwothree.phrases");
//
//    @DisplayName("!123")
//    @ParameterizedTest
//    @MethodSource("getCommands")
//    void handle(String inputText) {
//        TELEGRAM_MESSAGE_2.setText(inputText);
//        SendMessage sendMessage = textMessageMainHandler.handle(TELEGRAM_MESSAGE_2);
//        System.out.println("получили:" + sendMessage.getText());
//        assertThat(phrases).contains(sendMessage.getText());
//    }
//
//    @Test
//    void noHandle() {
//        TELEGRAM_MESSAGE_2.setText("тот не обработаем");
//        SendMessage sendMessage = textMessageMainHandler.handle(TELEGRAM_MESSAGE_2);
//        assertThat(sendMessage.getText()).isNull();
//    }
//
//    private static List<String> getCommands() {
//        return COMMANDS;
//    }
//}