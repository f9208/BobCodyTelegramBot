package ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.domain.Quote;
import ru.bobcody.domain.Type;
import ru.bobcody.services.QuoteService;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.TextConstantHandler;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Slf4j
//@Component
public class QuoteProducer {
    @Autowired
    private QuoteService quoteService;
    private final Random rand = new SecureRandom();

    public String getText(Message message, Type type) {
        String[] command = message.getText().toLowerCase().split(" ");
        Quote quote = null;
        if (command.length == 1) {
            log.info("get random quote");
            quote = getRandom(type);
        }
        if (command.length == 2) {
            try {
                long quoteId = Long.parseLong(command[1]);
                if (quoteId <= 0) throw new NumberFormatException();
                log.info("get quote with id {}", quoteId);
                quote = getById(quoteId, type);
                if (quote == null) return TextConstantHandler.QUOTE_NOT_FOUND;
            } catch (NumberFormatException e) {
                return TextConstantHandler.ONLY_POSITIVE_NUMBERS;
            }
        }
        return (quote != null)
                ? facade(quote)
                : TextConstantHandler.FOR_SEARCHING_TIP;
    }

    private Quote getRandom(Type type) {
        int id;
        if (type.equals(Type.CAPS)) {
            id = rand.nextInt((int) quoteService.getLastCapsId()) + 1;
            return getById(id, type);
        }
        if (type.equals(Type.REGULAR)) {
            id = rand.nextInt((int) quoteService.getLastRegularId()) + 1;
            return getById(id, type);
        }
        return null;
    }

    private Quote getById(long id, Type type) {
        if (type == Type.CAPS) return quoteService.getByCapsId(id);
        if (type == Type.REGULAR) return quoteService.getByRegularId(id);
        return null;
    }

    private String facade(Quote quote) {
        String result;
        StringBuilder master = new StringBuilder();
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        if (quote.getType().equals(Type.REGULAR)) {
            master.append("Цитата №").
                    append(quote.getRegularId())
                    .append(" (")
                    .append(quoteService.getLastRegularId());
        }
        if (quote.getType().equals(Type.CAPS)) {
            master.append("Капс №").
                    append(quote.getCapsId())
                    .append(" (")
                    .append(quoteService.getLastCapsId());
        }
        master.append(") added: ")
                .append(quote.getCreateDate().format(formatDateToPrint))
                .append("\n")
                .append(quote.getText());
        result = master.toString();
        return result;
    }
}