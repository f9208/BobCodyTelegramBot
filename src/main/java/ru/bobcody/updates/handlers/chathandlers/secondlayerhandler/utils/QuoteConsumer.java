package ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;
import ru.bobcody.domain.Guest;
import ru.bobcody.domain.Quote;
import ru.bobcody.domain.Type;
import ru.bobcody.services.QuoteService;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.TextConstantHandler;

import java.time.ZoneOffset;

import static java.time.LocalDateTime.ofEpochSecond;
import static ru.bobcody.CommonTextConstant.SMTH_GET_WRONG;

@Slf4j
//@Component
public class QuoteConsumer  {
    @Value("${chatid.admin}")
    private Long moderatorChatId;
    @Autowired
    private BobCodyBot bobCodyBot;
    @Autowired
    private QuoteService quoteService;

    public String approveCaps(Message message) {
        String result = SMTH_GET_WRONG;
        String textMessage = message.getText().toLowerCase();
        long quoteIdFromMessage = 0;

        if (textMessage.split(" ").length == 1) return TextConstantHandler.WHAT_ADD;
        if (textMessage.split(" ").length == 2) {
            try {
                quoteIdFromMessage = Long.parseLong(textMessage.split(" ")[1]);
            } catch (NumberFormatException e) {
                return TextConstantHandler.USE_NUMBERS;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            String error = checkDuplication(quoteIdFromMessage);
            if (error != null) {
                return error;
            }
            quoteService.approveCaps(quoteIdFromMessage);
            long capsId = quoteService.getCapsId(quoteIdFromMessage);
            result = TextConstantHandler.CAPS_HAS_ADDED + quoteService.getCapsId(quoteIdFromMessage);
            log.info("quote with id {} has been approved as caps-type. caps_id= {}", quoteIdFromMessage, capsId);
        }
        return result;
    }

    public String approveQuote(Message message) {
        String result = SMTH_GET_WRONG;
        String textMessage = message.getText().toLowerCase();
        long quoteIdFromMessage = 0;

        if (textMessage.split(" ").length == 1) return TextConstantHandler.WHAT_ADD;
        if (textMessage.split(" ").length == 2) {
            try {
                quoteIdFromMessage = Long.parseLong(textMessage.split(" ")[1]);
            } catch (NumberFormatException e) {
                return TextConstantHandler.USE_NUMBERS;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            String error = checkDuplication(quoteIdFromMessage);
            if (error != null) {
                return error;
            }
            quoteService.approveRegular(quoteIdFromMessage);
            long quoteId = quoteService.getRegularId(quoteIdFromMessage);
            result = TextConstantHandler.QUOTE_HAS_ADDED + quoteId;
            log.info("quote with id {} has been approved as regular-type. quote_id = {}", quoteIdFromMessage, quoteId);
        }
        return result;
    }

    public String addQuoteForApprove(Message message) {
        String replay;
        String textQuote;
        textQuote = message.getText().substring(3);
        if (textQuote.length() == 0) {
            replay = message.getFrom().getUserName() + ", " + TextConstantHandler.WRITE_QUOTE;
        } else if (textQuote.length() < 5000) {
            Quote quote = new Quote(
                    textQuote,
                    ofEpochSecond(message.getDate(), 0, ZoneOffset.of("+3")),
                    Type.ABYSS, new Guest(message.getFrom()));
            Quote saved = quoteService.save(quote);
            if (saved == null) return TextConstantHandler.SAVE_FAILURE;
            log.info("user {} add quote {}", message.getFrom().getFirstName(), quote.getText());
            replay = TextConstantHandler.ADD_TO_ABYSS;
            sendToModerator(message, saved);
        } else {
            replay = TextConstantHandler.TOO_LONG_TEXT;
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
            log.error("try call invalid id={}", id);
            return TextConstantHandler.NO_SUCH_ID;
        }
        if (hasApprovedAsCaps(id)) {
            log.error("try to approve afore approved caps with id={} ", id);
            return TextConstantHandler.HAS_APPROVED_AS_CAPS;
        }
        if (hasApprovedAsQuote(id)) {
            log.error("try to approve afore approved quite with id={}", id);
            return TextConstantHandler.HAS_APPROVED_AS_QUOTE;
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
