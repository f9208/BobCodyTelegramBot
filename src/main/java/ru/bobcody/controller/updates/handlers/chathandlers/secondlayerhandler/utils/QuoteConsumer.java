package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;
import ru.bobcody.data.entities.Guest;
import ru.bobcody.data.entities.Quote;
import ru.bobcody.data.entities.Type;
import ru.bobcody.data.services.QuoteService;

import java.time.ZoneOffset;

import static java.time.LocalDateTime.ofEpochSecond;

@Slf4j
@Component
public class QuoteConsumer { //todo вынести текст сообщений в константы
    private static final String CAPS_ADDED_MSG = "Капс добавлен за номером ";
    private static final String SMTH_GET_WRONG_MSG = "что-то пошло не так";
    @Value("${chatid.admin}")
    private Long moderatorChatId;
    @Autowired
    @Lazy
    @Setter
    private BobCodyBot bobCodyBot;
    @Autowired
    private QuoteService quoteService;

    public String approveCaps(Message message) {
        String result = "что то пошло не так";
        String textMessage = message.getText().toLowerCase();
        long quoteIdFromMessage = 0;

        if (textMessage.split(" ").length == 1) return "чо добавить то?";
        if (textMessage.split(" ").length == 2) {
            try {
                quoteIdFromMessage = Long.parseLong(textMessage.split(" ")[1]);
            } catch (NumberFormatException e) {
                return "цифры вводи.";
            } catch (ArrayIndexOutOfBoundsException e) {
                return "ой не влажу в массив";
            }
            String error = checkDuplication(quoteIdFromMessage);
            if (error != null) {
                return error;
            }
            quoteService.approveCaps(quoteIdFromMessage);
            long capsId = quoteService.getCapsId(quoteIdFromMessage);
            result = CAPS_ADDED_MSG + quoteService.getCapsId(quoteIdFromMessage);
            log.info("quote with id {} has been approved as caps-type. caps_id= {}", quoteIdFromMessage, capsId);
        }
        return result;
    }

    public String approveQuote(Message message) {
        String result = SMTH_GET_WRONG_MSG;
        String textMessage = message.getText().toLowerCase();
        long quoteIdFromMessage = 0;

        if (textMessage.split(" ").length == 1) return "чо добавить то?";
        if (textMessage.split(" ").length == 2) {
            try {
                quoteIdFromMessage = Long.parseLong(textMessage.split(" ")[1]);
            } catch (NumberFormatException e) {
                return "цифры вводи.";
            } catch (ArrayIndexOutOfBoundsException e) {
                return "ой не влажу в массив";
            }
            String error = checkDuplication(quoteIdFromMessage);
            if (error != null) {
                return error;
            }
            quoteService.approveRegular(quoteIdFromMessage);
            long quoteId = quoteService.getRegularId(quoteIdFromMessage);
            result = "Цитата добавлена за номером " + quoteId;
            log.info("quote with id {} has been approved as regular-type. quote_id = {}", quoteIdFromMessage, quoteId);
        }
        return result;
    }

    public String addQuoteForApprove(Message message) {
        String replay;
        String textQuote;
        textQuote = message.getText().substring(3);
        if (textQuote.length() == 0) {
            replay = message.getFrom().getUserName() + ", ты цитату то введи";
        } else if (textQuote.length() < 5000) {
            Quote quote = new Quote(
                    textQuote,
                    ofEpochSecond(message.getDate(), 0, ZoneOffset.of("+3")),
                    Type.ABYSS, new Guest(message.getFrom()));
            Quote saved = quoteService.save(quote);
            if (saved == null) return "не удалось сохранить цитату";
            log.info("user {} add quote {}", message.getFrom().getFirstName(), quote.getText());
            replay = "Записал в бездну. Цитата будет добавлена в хранилище после проверки модератором.";
            sendToModerator(message, saved);
        } else {
            replay = "Видимо, телеграмм отключил ограничение в 4096 символов на сообщение и ты как то смог в так длинно." +
                    " Но я не буду это сохранять, запиши себе в блокнот, потом поржешь";
        }
        return replay;
    }

    public void sendToModerator(Message message, Quote quote) {
        log.info("resend message to moderator");
        String textInputMessage = "пользователь " + message.getFrom().getUserName() +
                " добавил цитатку. \n" +
                "ID цитаты в бездне: " + quote.getId() +
                "\nText:\n" +
                message.getText().substring(3);
        try {
            SendMessage messageExecute = new SendMessage();
            messageExecute.setChatId(moderatorChatId.toString());
            messageExecute.setText(textInputMessage);
            bobCodyBot.execute(messageExecute);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String checkDuplication(long id) {
        if (notFound(id)) {
            log.error("попытка вызова несуществующего id={}", id);
            return "нет такого айди";
        }
        if (hasApprovedAsCaps(id)) {
            log.error("попытка утвердить уже утвержденный капс с id={} ", id);
            return "цитату за этим номером уже апрували как капс";
        }
        if (hasApprovedAsQuote(id)) {
            log.error("попытка утвердить уже утвержденную цитату с id={}", id);
            return "цитату за этим номером уже апрували как обычную цитату";
        }
        return null;
    }

    private boolean hasApprovedAsCaps(long id) {
        return quoteService.getById(id).getCapsId() != 0;
    }

    private boolean hasApprovedAsQuote(long id) {
        return quoteService.getById(id).getRegularId() != 0;
    }

    private boolean notFound(long id) {
        return quoteService.getById(id) == null;
    }
}
