package ru.multibot.bobcody.controller.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.SQL.Entities.QuotationsBook;
import ru.multibot.bobcody.controller.SQL.Servies.QuotationsBookServiceImp;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "quotemaster")
public class QuoteHandler {

    Integer master;
    @Autowired
    QuotationsBookServiceImp quotationsBookServiceImp;

    public SendMessage addQuote(Message message) {
        QuotationsBook quote = new QuotationsBook(message.getFrom().getUserName(),
                message.getDate(),
                message.getText().substring(3));


        SendMessage replay = new SendMessage();
        Long number = quotationsBookServiceImp.add(quote);

        replay.setText("цитата добавлена под номером " + number.toString());
        return replay;
    }

    public SendMessage deleteQuote(Message message) {

        SendMessage replay = new SendMessage();
        replay.setText(message.getFrom().getUserName() + ", ты кто такой? давай, досвидания.");
        if (message.getFrom().getId().equals(master)) {
            Long id = Long.valueOf(message.getText().split(" ")[1]);
            try {
                quotationsBookServiceImp.deleteById(id);
            } catch (EmptyResultDataAccessException e) {
                return replay.setText("цитата под номером " + id + " не найдена");
            }
            return replay.setText("цитата под номером " + id + " удалена");
        }

        return replay;
    }

    public SendMessage randomQuote(Message message) {
        return new SendMessage().setText("рендом пока не работает");
    }

    //костыль. должен быть запрос, возвращающий размер таблицы. вот он: SELECT count(*) from public.quotation;
    public int getSizeQuoteDB() {
        return quotationsBookServiceImp.getAll().size();
    }
}
