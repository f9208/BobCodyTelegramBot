package ru.multibot.bobcody.controller.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteBook;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteBookServiceImp;

import java.util.NoSuchElementException;
import java.util.Random;

@Component
@Setter
@Getter
public class QuoteBookHandler {
    @Autowired
    QuoteBookServiceImp quoteBookServiceImp;

    public String getQuoteBook(Message message) {
        String cutWord = message.getText().trim();
        if (cutWord.equals("!ц") ||
                cutWord.equals("!Ц") ||
                cutWord.equals("!Q") ||
                cutWord.equals("!q")) {
            return randomQuoteFromQuoteBook();
        }

        String fullWord = message.getText();
        try {
            return getQuoteFromBookById(Long.valueOf(fullWord.split(" ")[1]));
        } catch (NumberFormatException e) {
            return "в качестве номера цитаты используйте только цифры (числа)";
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String randomQuoteFromQuoteBook() {
        Random r = new Random();
        Long temp = Long.valueOf(r.nextInt(quoteBookServiceImp.getSizeDB()));
        return getQuoteFromBookById(temp + 1);
    }

    public String getQuoteFromBookById(Long id) {
        String result;
        Long number = id;
        StringBuilder master = new StringBuilder();
        try {
            QuoteBook current = quoteBookServiceImp.getById(Long.valueOf(number));
            master.append("Цитата №")
                    .append(current.getQuotationId())
                    .append(" (")
                    .append(quoteBookServiceImp.getSizeDB())
                    .append(") \n")
                    .append(current.getText());
            result=master.toString();
        } catch (NoSuchElementException e) {
            result=("нету такой");
        }
        return result;
    }

}
