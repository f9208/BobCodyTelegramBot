//package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import ru.bobcody.BobCodyBot;
//import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils.QuoteConsumer;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//import static ru.bobcody.data.services.manual.TelegramMessageData.*;
//
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//class QuoteSetHandlerTest extends AbstractSpringBootStarterTest {
//    private final TextMessageMainHandler textMessageMainHandler;
//    private final QuoteConsumer quoteConsumer;
//    private final BobCodyBot bobCodyBot = Mockito.mock(BobCodyBot.class);
//
//    @ParameterizedTest
//    @ValueSource(strings = {"!дц текст цитаты", "!aq text quote"})
//    void addQuote(String commands) throws TelegramApiException {
//        when(bobCodyBot.execute(new SendMessage())).thenReturn(TELEGRAM_MESSAGE_1);
//        quoteConsumer.setBobCodyBot(bobCodyBot);
//
//        TELEGRAM_MESSAGE_1.setText(commands);
//        assertThat(textMessageMainHandler.handle(TELEGRAM_MESSAGE_1).getText())
//                .isEqualTo("Записал в бездну. Цитата будет добавлена в хранилище после проверки модератором.");
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"!approvequote 2"})
//    void approveQuote(String commands) {
//        TELEGRAM_MESSAGE_6.setText(commands);
//        TELEGRAM_MESSAGE_2.setText("!q 2"); //смотрим, что цитаты с таким номером нет
//        assertThat(textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText())
//                .isEqualTo("цитаты с таким id не найдено");
//        System.out.println(TELEGRAM_MESSAGE_6);
//        String answer = textMessageMainHandler.handle(TELEGRAM_MESSAGE_6).getText();
//        assertThat(answer).isEqualTo("Цитата добавлена за номером 2");
//        assertThat(textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText())
//                .doesNotStartWith("для поиска цитат");
//        assertThat(textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText())
//                .isEqualTo("Цитата №2 (2) added: 2021.11.27\nобычная цитата 2");
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"!approvecaps 2"})
//    void approveCaps(String commands) {
//        TELEGRAM_MESSAGE_6.setText(commands);
//        TELEGRAM_MESSAGE_2.setText("!caps 2"); //смотрим, что капса с таким номером нет
//        assertThat(textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText())
//                .isEqualTo("цитаты с таким id не найдено");
//        String answer = textMessageMainHandler.handle(TELEGRAM_MESSAGE_6).getText();
//        assertThat(answer).isEqualTo("Капс добавлен за номером 2");
//        assertThat(textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText())
//                .doesNotStartWith("для поиска цитат");
//        assertThat(textMessageMainHandler.handle(TELEGRAM_MESSAGE_2).getText())
//                .isEqualTo("Капс №2 (2) added: 2021.11.27\nобычная цитата 2");
//    }
//}
