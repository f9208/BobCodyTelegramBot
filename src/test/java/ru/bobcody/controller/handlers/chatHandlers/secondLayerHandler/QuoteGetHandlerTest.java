package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.services.QuoteService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.services.data.QuoteDate.QUOTE_1_ABYSS;
import static ru.bobcody.services.data.QuoteDate.QUOTE_2_ABYSS;
import static ru.bobcody.services.data.TelegramMessageData.TELEGRAM_MESSAGE_2;

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