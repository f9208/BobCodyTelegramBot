package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteStorage;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteStorageServiceImp;

import java.util.NoSuchElementException;
import java.util.Random;

@Component
@Setter
@Getter
public class QuoteStorageHandler {
    @Autowired
    QuoteStorageServiceImp quoteStorageServiceImp;

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
            return getQuoteFromStorageById(Long.valueOf(fullWord.split(" ")[1]));
        } catch (NumberFormatException e) {
            return "в качестве номера цитаты используйте только цифры (числа)";
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String randomQuoteFromQuoteStorage() {
        Random r = new Random();
        Long temp;
        temp = Long.valueOf(r.nextInt(quoteStorageServiceImp.getMaxID()))+1;
        /* в случае если какую то из цитат "грохнули" ее id-шник уже не будет показываться
        * и его уже никто не займет - особенность sql. поэтому предварительно проверяем
        * закреплена ли какая то цитата за генерируемым рендомным числом
        * - если нет, то генерируем новое число. инкремент - чтобы крайняя цитата тоже попадала в выборку
        * */
        while (!exists(temp)) {
            temp = Long.valueOf(r.nextInt(quoteStorageServiceImp.getMaxID()))+1;
        }
        return getQuoteFromStorageById(temp);
    }

    public String getQuoteFromStorageById(Long id) {
        String result;
        Long number = id;
        StringBuilder master = new StringBuilder();
        try {
            QuoteStorage current = quoteStorageServiceImp.getById(Long.valueOf(number));
            master.append("Цитата №")
                    .append(current.getQuotationId())
                    .append(" (")
                    .append(quoteStorageServiceImp.getMaxID())
                    .append(") \n")
                    .append(current.getText());
            result = master.toString();
        } catch (NoSuchElementException e) {
            result = ("нету такой");
        }
        return result;
    }


    public String approvingQuote(int id) {
        String result = "что то пошло не так";
        try {
            int addedId = quoteStorageServiceImp.adderQuote(id);
            result = "цитата добавлена за номером " + addedId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(Long id) {
        return quoteStorageServiceImp.existById(id);
    }

}
