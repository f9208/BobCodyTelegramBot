package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.entities.QuoteStorage;
import ru.bobcody.services.QuoteStorageService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@Setter
@Getter
public class QuoteStorageHandler implements SimpleHandlerInterface {
    @Autowired
    QuoteStorageService quoteStorageServiceImp;
    @Value("${quote.get.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(getTextQuoteStorage(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private String getTextQuoteStorage(Message message) {
        String[] command = message.getText().toLowerCase().split(" ");
        if (command.length == 1) {
            log.info("get random quote");
            return randomQuoteFromQuoteStorage();
        }
        if (command.length == 2) {
            try {
                long quoteId = Long.parseLong(command[1]);
                log.info("get quote with id {}", quoteId);
                return textQuotePrettyById(quoteId);
            } catch (NumberFormatException e) {
                return "в качестве номера цитаты используйте только цифры (числа)";
            }
        }
        return "для поиска цитат используйте синтаксис: !q + номер_цитаты_цифрами";
    }

    private String randomQuoteFromQuoteStorage() {
        Random r = new Random();
        Long temp;
        temp = (long) r.nextInt(quoteStorageServiceImp.getMaxID().intValue()) + 1;
        /* в случае если какую то из цитат "грохнули" ее quoteId-шник уже не будет показываться
         * и его уже никто не займет - особенность sql. поэтому предварительно проверяем
         * закреплена ли какая то цитата за генерируемым рендомным числом
         * - если нет, то генерируем новое число. инкремент - чтобы крайняя цитата тоже попадала в выборку
         * */
        while (!existsById(temp)) {
            temp = (long) r.nextInt(quoteStorageServiceImp.getMaxID().intValue()) + 1;
        }
        log.info("generated quote id={}", temp);
        return textQuotePrettyById(temp);
    }

    private String textQuotePrettyById(Long id) {
        String result;
        Long number = id;
        StringBuilder master = new StringBuilder();
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        try {
            QuoteStorage current = quoteStorageServiceImp.getSingleQuoteFromStorageById(number);
            if (current != null) {
                master.append("Цитата №")
                        .append(current.getId())
                        .append(" (")
                        .append(quoteStorageServiceImp.getMaxID())
                        .append(") added: ")
                        .append(LocalDateTime.ofInstant(Instant.ofEpochSecond(current.getDateAdded()), ZoneId.of("GMT")).format(formatDateToPrint))
                        .append("\n")
                        .append(current.getText());
                result = master.toString();
            } else result = "нету такой.";
        } catch (Exception e) {
            e.printStackTrace();
            result = ("что-то  в цитатнике пошло не так");
        }
        return result;
    }

    private boolean existsById(Long id) {
        return quoteStorageServiceImp.existById(id);
    }
}