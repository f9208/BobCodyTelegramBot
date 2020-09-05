package ru.multibot.bobcody.controller.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteServiceImp;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "quotemaster")
public class QuoteHandler {
    @Autowired
    QuoteServiceImp quoteServiceImp;

    public String addQuote(Message message) {
        String replay;
        String textQuote;
        textQuote = message.getText().substring(3);
        if (textQuote.length() == 0) {
            replay=message.getFrom().getUserName() + ", ты цитату то введи";

        } else if (textQuote.length() < 5000) {
            Quote quote = new Quote(new Guest(message.getFrom()).getUserID(),
                    message.getDate(),
                    textQuote);
            quoteServiceImp.add(quote);
            replay="Записал. Цитата будет добавлена в хранилище после проверки модератором.";
        } else {
            replay="Длина цитаты ограничена 5000 символами. Запиши себе в блокнот, потом поржешь";
        }

        return replay;
    }

}
