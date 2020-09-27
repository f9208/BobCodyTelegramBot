package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.multibot.bobcody.BobCodyBot;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;
import ru.multibot.bobcody.controller.SQL.Servies.QuoteAbyssServiceImp;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "quotemaster")
public class QuoteAbyssHandler {
    @Autowired
    QuoteAbyssServiceImp quoteAbyssServiceImp;
    @Autowired
    BobCodyBot bobCodyBot;

    public String addQuoteToAbyss(Message message) {
        sendToModerator(message);
        String replay;
        String textQuote;
        textQuote = message.getText().substring(3);
        if (textQuote.length() == 0) {
            replay = message.getFrom().getUserName() + ", ты цитату то введи";

        } else if (textQuote.length() < 5000) {
            Quote quote = new Quote(new Guest(message.getFrom()).getUserID(),
                    message.getDate(),
                    textQuote);
            quoteAbyssServiceImp.add(quote);
            replay = "Записал. Цитата будет добавлена в хранилище после проверки модератором.";
        } else {
            replay = "Длина цитаты ограничена 5000 символами. Запиши себе в блокнот, потом поржешь";
        }

        return replay;
    }

    //шлет сообщение мне в личку в случае добавления цитат в бездну
    public void sendToModerator(Message message) {
        String textInputMessage = "пользователь " + message.getFrom().getUserName() +
                " добавил цитатку:\n"
                + message.getText().substring(3, message.getText().length());
        try {
            bobCodyBot.execute(new SendMessage().setChatId("445682905").setText(textInputMessage));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
