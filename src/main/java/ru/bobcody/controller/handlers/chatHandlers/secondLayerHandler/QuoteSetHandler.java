package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.entities.Guest;
import ru.bobcody.entities.Quote;
import ru.bobcody.entities.Type;
import ru.bobcody.services.QuoteService;

import java.time.ZoneOffset;
import java.util.List;

import static java.time.LocalDateTime.ofEpochSecond;

@Slf4j
@Data
@Component
public class QuoteSetHandler implements SimpleHandlerInterface {
    @Value("${quote.set.command}")
    List<String> commands;
    @Value("${chatid.admin}")
    String moderatorChatId;
    @Autowired
    QuoteService quoteService;
    @Autowired
    BobCodyBot bobCodyBot;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage(); //todo где то надо чекать что апрувает админ
        if (inputMessage.getText().trim().startsWith("!approvecaps")) {
            result.setText(approveCaps(inputMessage));
            return result;
        }
        if (inputMessage.getText().trim().startsWith("!approvequote")) {
            result.setText(approveQuote(inputMessage));
            return result;
        }
        if (inputMessage.getText().trim().startsWith("!aq") ||
                inputMessage.getText().trim().startsWith("!дц")) {
            String replay = addQuoteForApprove(inputMessage);
            result.setChatId(inputMessage.getChatId().toString());
            result.setText(replay);
            return result;
        }
        return result;
    }


    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private String approveCaps(Message message) {
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
            String error = checkError(quoteIdFromMessage);
            if (error != null) {
                log.info(error); //todo переделать на нормальное
                return error;
            }
            quoteService.approveCaps(quoteIdFromMessage);
            long caps_id = quoteService.getCapsId(quoteIdFromMessage);
            result = "Капс добавленна за номером " + quoteService.getCapsId(quoteIdFromMessage);
            log.info("quote with id {} has been approved as caps-type. caps_id= {}", quoteIdFromMessage, caps_id);
        }
        return result;
    }

    private String approveQuote(Message message) {
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
            String error = checkError(quoteIdFromMessage);
            if (error != null) return error;
            quoteService.approveRegular(quoteIdFromMessage);
            long quoteId = quoteService.getRegularId(quoteIdFromMessage);
            result = "Цитата добавлена за номером " + quoteId;
            log.info("quote with id {} has been approved as regular-type. quote_id = {}", quoteIdFromMessage, quoteId);
        }
        return result;
    }

    private String addQuoteForApprove(Message message) {
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
            Quote saved = quoteService.save(quote); //todo здесь бы проверять сохранилась или нет
            log.info("user {} add quote {}", message.getFrom().getFirstName(), quote.getText());
            replay = "Записал в бездну. Цитата будет добавлена в хранилище после проверки модератором.";
            sendToModerator(message, saved);
        } else {
            replay = "Видимо, телеграмм отключил ограничение в 4096 символов на сообщение и ты как то смог в так длинно." +
                    " Но я не буду это сохранять, запиши себе в блокнот, потом поржешь";
        }
        return replay;
    }

    private void sendToModerator(Message message, Quote quote) {
        log.info("resend message to moderator");
        String textInputMessage = "пользователь " + message.getFrom().getUserName() +
                " добавил цитатку. \n" +
                "ID цитаты в бездне: " + quote.getId() +
                "\nText:\n" +
                message.getText().substring(3);
        try {
            SendMessage messageExecute = new SendMessage();
            messageExecute.setChatId(moderatorChatId);
            messageExecute.setText(textInputMessage);
            bobCodyBot.execute(messageExecute);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String checkError(long id) { //todo как то подругому назвать метод, это не ошибки, скорее наложения
        if (notFound(id)) {
            return "нет такого айди";
        }
        if (hasApprovedAsCaps(id)) {
            return "цитату за этим номером уже апрували как капс";
        }
        if (hasApprovedAsQuote(id)) {
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
