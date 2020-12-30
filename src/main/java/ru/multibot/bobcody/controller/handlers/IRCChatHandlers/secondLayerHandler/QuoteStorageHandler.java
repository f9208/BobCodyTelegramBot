package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.SQL.Entities.QuoteInsideStorage;
import ru.multibot.bobcody.SQL.Servies.QuoteAbyssService;
import ru.multibot.bobcody.SQL.Servies.QuoteStorageService;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Setter
@Getter
public class QuoteStorageHandler implements SimpleHandlerInterface {
    @Autowired // заменить на интерфейс?
            QuoteStorageService quoteStorageServiceImp;
    @Autowired  // заменить на интерфейс?
            QuoteAbyssService quoteAbyssServiceImp;

    private String getQuoteStorage(Message message) {
        String shortCommand = message.getText().trim();
        if (shortCommand.equals("!ц") ||
                shortCommand.equals("!цитата") ||
                shortCommand.equals("!quote") ||
                shortCommand.equals("!q")) {
            return randomQuoteFromQuoteStorage();
        }

        String fullCommand = message.getText();
        try {
            return getTextQuoteFromStorageById(Long.valueOf(fullCommand.split(" ")[1]));
        } catch (NumberFormatException e) {
            return "в качестве номера цитаты используйте только цифры (числа)";
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private String randomQuoteFromQuoteStorage() {
        Random r = new Random();
        Long temp;
        temp = Long.valueOf(r.nextInt(quoteStorageServiceImp.getMaxID().intValue())) + 1;
        /* в случае если какую то из цитат "грохнули" ее quoteId-шник уже не будет показываться
         * и его уже никто не займет - особенность sql. поэтому предварительно проверяем
         * закреплена ли какая то цитата за генерируемым рендомным числом
         * - если нет, то генерируем новое число. инкремент - чтобы крайняя цитата тоже попадала в выборку
         * */
        while (!existsById(temp)) {
            temp = Long.valueOf(r.nextInt(quoteStorageServiceImp.getMaxID().intValue())) + 1;
        }
        return getTextQuoteFromStorageById(temp);
    }

    private String getTextQuoteFromStorageById(Long id) {
        String result;
        Long number = id;
        StringBuilder master = new StringBuilder();
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        try {
            QuoteInsideStorage current = quoteStorageServiceImp.getSingleQuoteFromStorageById(Long.valueOf(number));
            if (current != null) {
                master.append("Цитата №")
                        .append(current.getQuoteId())
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
            result = ("что-то пошло не так");
        }
        return result;
    }

    private String approvingQuote(Message inputMessage) {
        String result = "что то пошло не так";
        String textMessage = inputMessage.getText().toLowerCase();
        Long inputQuoteIdFromAbysse;

        if (textMessage.split(" ").length == 1) return "чо добавить то?";
        if (textMessage.split(" ").length == 2) {
            try {
                inputQuoteIdFromAbysse = Long.valueOf(textMessage.split(" ")[1]);
            } catch (NumberFormatException e) {
                return "цифры вводи.";
            }
            // есть такой ID в бездне?
            if (quoteAbyssServiceImp.containtInAbyss(inputQuoteIdFromAbysse)) {
                Long dateFromeAbysse = quoteAbyssServiceImp.getDateAddedByQuoteId(inputQuoteIdFromAbysse);
                Long authorId = quoteAbyssServiceImp.getAuthorIdByQuoteId(inputQuoteIdFromAbysse);

                // на случай если (ВДРУГ) одномоментно из двух разных чатов будут добавляться цитаты - проверяем еще и автора цитаты на уникальность. можно конечно попробовать взять всю цитату и унаследовать комперебл...
                if (quoteStorageServiceImp.existByDate(dateFromeAbysse)
                        && quoteStorageServiceImp.getAuthorByDateAdded(dateFromeAbysse).longValue()
                        == authorId.longValue()) {
                    return "ее уже добавляли в хранилище.";
                } else {
                    Long resultNumber = quoteStorageServiceImp.adderQuote(inputQuoteIdFromAbysse);
                    result = "цитата добавленна за номером " + resultNumber.longValue();
                }

            } else {
                result = "нет такой цитаты в бездне. трай аген";
            }
        }
        return result;
    }

    private boolean existsById(Long id) {
        return quoteStorageServiceImp.existById(id);
    }

    private boolean existByDateAdded(Long date) {
        return quoteStorageServiceImp.existByDate(date);
    }

    private QuoteInsideStorage getQuote(Long id) {
        return quoteStorageServiceImp.getSingleQuoteFromStorageById(id);
    }

    /**
     * предполагается, что команда !добавь будет обрабатываться только модератором
     * с chatID=445682905
     * другие команды соответствующим методом
     */
    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getText().startsWith("!добавь") && inputMessage.getChatId() == 445682905) {
            return result.setText(approvingQuote(inputMessage));
        }
        result.setText(getQuoteStorage(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!ц");
        commands.add("!quote");
        commands.add("!q");
        commands.add("!цитата");
        commands.add("!добавь");
        return commands;
    }
}