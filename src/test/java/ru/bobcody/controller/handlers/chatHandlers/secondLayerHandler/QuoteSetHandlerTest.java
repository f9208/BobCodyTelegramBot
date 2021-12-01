package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.bobcody.services.data.TelegramMessageData.TELEGRAM_MESSAGE_1;
import static ru.bobcody.services.data.TelegramMessageData.TELEGRAM_MESSAGE_2;
import static ru.bobcody.services.data.TelegramUser.DMITRY_TELEGRAM;

class QuoteSetHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    QuoteSetHandler quoteSetHandler;
    BobCodyBot bobCodyBot = Mockito.mock(BobCodyBot.class);
    @Value("${chatid.admin}")
    Long moderatorId;

    @ParameterizedTest
    @ValueSource(strings = {"!дц текст цитаты", "!aq text quote"})
    void addQuote(String commands) throws TelegramApiException {
        when(bobCodyBot.execute(new SendMessage())).thenReturn(TELEGRAM_MESSAGE_1);
        quoteSetHandler.setBobCodyBot(bobCodyBot);

        TELEGRAM_MESSAGE_1.setText(commands);
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText())
                .isEqualTo("Записал в бездну. Цитата будет добавлена в хранилище после проверки модератором.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"!approvequote 2"})
    void approveQuote(String commands) {
        DMITRY_TELEGRAM.setId(moderatorId);
        TELEGRAM_MESSAGE_1.setText(commands);
        TELEGRAM_MESSAGE_2.setText("!q 2"); //смотрим, что цитаты с таким номером нет
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText())
                .isEqualTo("для поиска цитат используйте синтаксис: !q + номер_цитаты_цифрами");
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("Цитата добавлена за номером 2");
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText())
                .doesNotStartWith("для поиска цитат");
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText())
                .isEqualTo("Цитата №2 (2) added: 2021.11.27\nобычная цитата 2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"!approvecaps 2"})
    void approveCaps(String commands) {
        DMITRY_TELEGRAM.setId(moderatorId);
        TELEGRAM_MESSAGE_1.setText(commands);
        TELEGRAM_MESSAGE_2.setText("!caps 2"); //смотрим, что капса с таким номером нет
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText())
                .isEqualTo("для поиска цитат используйте синтаксис: !q + номер_цитаты_цифрами");
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("Капс добавлен за номером 2");
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText())
                .doesNotStartWith("для поиска цитат");
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText())
                .isEqualTo("Капс №2 (2) added: 2021.11.27\nобычная цитата 2");
    }
}
