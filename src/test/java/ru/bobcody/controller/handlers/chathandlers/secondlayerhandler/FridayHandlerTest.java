//package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.format.TextStyle;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static ru.bobcody.controller.handlers.chathandlers.PropertiesUtils.getCommandsByKey;
//import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_2;
//
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//class FridayHandlerTest extends AbstractSpringBootStarterTest {
//    private final static List<String> COMMANDS = getCommandsByKey("friday.command");
//
//    private final TextMessageMainHandler textMessageMainHandler;
//
//    @ParameterizedTest
//    @MethodSource("getCommands")
//    void handle(String inputMessage) {
//        TELEGRAM_MESSAGE_2.setText(inputMessage);
//        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
//            String answer = textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText();
//            assertThat(answer).isEmpty();
//        } else {
//            String[] exp = textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText().split("[\\s+\\.!]");
//            assertThat(answers()).containsAnyOf(exp);
//        }
//    }
//
//    private List<String> answers() {
//        List<String> results = new ArrayList<>();
//        DayOfWeek d = DayOfWeek.MONDAY;
//        for (int i = 0; i < 7; i++) {
//            results.add(d.getDisplayName(TextStyle.FULL,
//                    new Locale("ru", "RU")));
//            d = d.plus(1);
//        }
//        return results;
//    }
//
//    private static List<String> getCommands() {
//        return COMMANDS;
//    }
//}