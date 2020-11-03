package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteInsideStorage;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteAbyssService;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteAbyssServiceImp;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteStorageService;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteStorageServiceImp;

import java.util.NoSuchElementException;
import java.util.Random;

@Component
@Setter
@Getter
public class QuoteStorageHandler {
    @Autowired // заменить на интерфейс?
            QuoteStorageService quoteStorageServiceImp;
    @Autowired  // заменить на интерфейс?
            QuoteAbyssService quoteAbyssServiceImp;

    public String getQuoteStorage(Message message) {
        String cutWord = message.getText().trim();
        if (cutWord.equals("!ц") ||
                cutWord.equals("!Ц") ||
                cutWord.equals("!Q") ||
                cutWord.equals("!q")) {
            return randomQuoteFromQuoteStorage();
        }

        String fullWord = message.getText();
        try {
            return getTextQuoteFromStorageById(Long.valueOf(fullWord.split(" ")[1]));
        } catch (NumberFormatException e) {
            return "в качестве номера цитаты используйте только цифры (числа)";
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String randomQuoteFromQuoteStorage() {
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

    public String getTextQuoteFromStorageById(Long id) {
        String result;
        Long number = id;
        StringBuilder master = new StringBuilder();
        try {
            QuoteInsideStorage current = quoteStorageServiceImp.getSingleQuoteFromStorageById(Long.valueOf(number));
            if (current != null) {
                master.append("Цитата №")
                        .append(current.getQuoteId())
                        .append(" (")
                        .append(quoteStorageServiceImp.getMaxID())
                        .append(") \n")
                        .append(current.getText());
                result = master.toString();
            } else result = "нету такой.";
        } catch (Exception e) {
            e.printStackTrace();
            result = ("что-то пошло не так");
        }
        return result;
    }

    public String approvingQuote(Message inputMessage) {
        String result = "что то пошло не так";
        String textMessage = inputMessage.getText().toLowerCase();
        Long inputQuoteIdFromAbysse;

        if (textMessage.split(" ").length == 1) return "чо добавить то?";
        if (textMessage.split(" ").length == 2) {
            try {
                inputQuoteIdFromAbysse = Long.valueOf(textMessage.split(" ")[1]);
            }
            catch (NumberFormatException e) {
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

    public boolean existsById(Long id) {
        return quoteStorageServiceImp.existById(id);
    }

    public boolean existByDateAdded(Long date) {
        return quoteStorageServiceImp.existByDate(date);
    }

    public QuoteInsideStorage getQuote(Long id) {
        return quoteStorageServiceImp.getSingleQuoteFromStorageById(id);
    }
}
