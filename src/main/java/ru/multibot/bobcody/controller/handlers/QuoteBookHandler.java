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

    public SendMessage getQuoteBook(Message message) {
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
            return new SendMessage().setText("в качестве номера цитаты используйте только цифры (числа)");
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public SendMessage randomQuoteFromQuoteBook() {
        Random r = new Random();
        Long temp = Long.valueOf(r.nextInt(quoteBookServiceImp.getSizeDB()));
        System.out.println(temp);
        return getQuoteFromBookById(temp + 1);
    }

    public SendMessage getQuoteFromBookById(Long id) {
        SendMessage replay = new SendMessage();
        Long number = id;
        StringBuilder result = new StringBuilder();
        try {
            QuoteBook current = quoteBookServiceImp.getById(Long.valueOf(number));
            result.append("Цитата №")
                    .append(current.getQuotationId())
                    .append(" (")
                    .append(quoteBookServiceImp.getSizeDB())
                    .append(") \n")
                    .append(current.getText());
            replay.setText(result.toString());
        } catch (NoSuchElementException e) {
            replay.setText("нету такой");
        }
        return replay;
    }

}
