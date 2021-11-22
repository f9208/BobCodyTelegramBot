package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.entities.CapsQuoteStorage;
import ru.bobcody.services.CapsQuoteStorageService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class CapsQuoteStorageHandler implements SimpleHandlerInterface {
    @Autowired
    CapsQuoteStorageService capsQuoteStorageService;
    @Value("${capsquote.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(getCapsQuote(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    /**
     * withoutNumberCommand - команда без указания номера цитаты: !caps
     * withNumberCommand - команда с указанием номера цитаты: !caps 34
     */
    private String getCapsQuote(Message inputMessage) {
        String withoutNumberCommand = inputMessage.getText().trim();
        if ("!к".equals(withoutNumberCommand) ||
                "!капс".equals(withoutNumberCommand) ||
                "!caps".equals(withoutNumberCommand)) {
            return randomQuoteFromQuoteStorage();
        }
        String withNumberCommand = inputMessage.getText();
        Long id = Long.valueOf(withNumberCommand.split(" ")[1]);
        try {
            log.info("request capsQuote id: {}", id);
            return capsQuotePrettyTextById(id);
        } catch (NumberFormatException e) {
            return "в качестве номера цитаты используйте только числа";
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private String capsQuotePrettyTextById(Long id) {
        log.info("try to sent capsQuote with id {}", id);
        String result;
        StringBuilder master = new StringBuilder();
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        try {
            CapsQuoteStorage current = capsQuoteStorageService.getById(id);
            if (current != null) {
                master.append("Капс №")
                        .append(current.getId())
                        .append(" (")
                        .append(capsQuoteStorageService.getMaxID())
                        .append(") added: ")
                        .append(LocalDateTime.ofInstant(Instant.ofEpochSecond(current.getDateAdded()),
                                ZoneId.of("GMT")).format(formatDateToPrint))
                        .append("\n")
                        .append(current.getCapsQuoteText());
                result = master.toString();
            } else result = "нету такой.";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("capsQuote service is failure");
            result = ("что-то с капсами не так. и в горле першит");
        }
        return result;
    }

    private String randomQuoteFromQuoteStorage() {
        log.info("get random capsQuote");
        Random r = new Random();
        Long temp = (long) r.nextInt(capsQuoteStorageService.getMaxID().intValue()) + 1;
        while (!capsQuoteStorageService.existById(temp)) {
            temp = (long) r.nextInt(capsQuoteStorageService.getMaxID().intValue()) + 1;
        }
        return capsQuotePrettyTextById(temp);
    }
}
