package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;
import ru.bobcody.data.services.QuoteService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.QuoteDate.QUOTE_1_ABYSS;
import static ru.bobcody.data.services.manual.QuoteDate.QUOTE_2_ABYSS;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_1;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_2;

class QuoteGetHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    QuoteService quoteService;
    String regexpQuote = "Цитата (([0-9№\\(\\)])+\\s)+added: 20[0-9][0-9]\\.[01][0-2]\\.[0-3][0-9]\n.*";
    String regexpCaps = "Капс (([0-9№\\(\\)])+\\s)+added: 20[0-9][0-9]\\.[01][0-2]\\.[0-3][0-9]\n.*";

    //добавим в базу еще по одной цитате и капсу
    @BeforeEach
    void init() {
        quoteService.approveRegular(QUOTE_1_ABYSS.getId());
        quoteService.approveCaps(QUOTE_2_ABYSS.getId());
    }

    @ParameterizedTest
    @MethodSource(value = {"getQuoteWithoutId", "getQuoteWithId"})
    void handleQuote(String inputText) {
        TELEGRAM_MESSAGE_2.setText(inputText);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText();
        assertThat(answer).matches(Pattern.compile(regexpQuote));
    }

    @ParameterizedTest
    @MethodSource(value = {"getCapsWithoutId", "getCapsWithId"})
    void handleCaps(String inputText) {
        TELEGRAM_MESSAGE_2.setText(inputText);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText();
        assertThat(answer).matches(Pattern.compile(regexpCaps));
    }

    @ParameterizedTest
    @ValueSource(strings = {"!q 29034"})
    void getQuoteNotFound(String inputMessage) {
        TELEGRAM_MESSAGE_1.setText(inputMessage);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("цитаты с таким id не найдено");
    }

    @ParameterizedTest
    @ValueSource(strings = {"!q -1", "!q 0", "!ц ашо"})
    void getQuoteInvalidId(String inputMessage) {
        TELEGRAM_MESSAGE_1.setText(inputMessage);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("в качестве номера цитаты используйте только положительные числа");
    }

    @ParameterizedTest
    @ValueSource(strings = {"!капс 29034", "!caps 234"})
    void getCapsNotFound(String inputMessage) {
        TELEGRAM_MESSAGE_1.setText(inputMessage);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("цитаты с таким id не найдено");
    }

    @ParameterizedTest
    @ValueSource(strings = {"!caps -1", "!капс 0", "!caps ашо"})
    void getCapsInvalidId(String inputMessage) {
        TELEGRAM_MESSAGE_1.setText(inputMessage);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("в качестве номера цитаты используйте только положительные числа");
    }

    private static List<String> getQuoteWithoutId() {
        return Lists.list("!ц", "!quote", "!q", "!цитата");
    }

    private static List<String> getQuoteWithId() {
        List<String> results = new ArrayList();
        getQuoteWithoutId().stream().map((q) -> q + " 1").forEach(results::add);
        getQuoteWithoutId().stream().map((q) -> q + " 2").forEach(results::add);
        return results;
    }

    private static List<String> getCapsWithoutId() {
        return Lists.list("!капс", "!к", "!caps");
    }

    private static List<String> getCapsWithId() {
        List<String> results = new ArrayList();
        getCapsWithoutId().stream().map((q) -> q + " 1").forEach(results::add);
        getCapsWithoutId().stream().map((q) -> q + " 2").forEach(results::add);
        return results;
    }
}