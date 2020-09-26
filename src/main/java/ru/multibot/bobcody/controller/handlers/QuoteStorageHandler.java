package ru.multibot.bobcody.controller.handlers;

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
        Long temp = Long.valueOf(r.nextInt(quoteStorageServiceImp.getSizeDB()));
        return getQuoteFromStorageById(temp + 1);
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
                    .append(quoteStorageServiceImp.getSizeDB())
                    .append(") \n")
                    .append(current.getText());
            result=master.toString();
        } catch (NoSuchElementException e) {
            result=("нету такой");
        }
        return result;
    }

}
