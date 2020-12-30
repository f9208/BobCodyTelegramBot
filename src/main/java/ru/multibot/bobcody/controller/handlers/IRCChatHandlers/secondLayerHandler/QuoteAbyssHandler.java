package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.multibot.bobcody.controller.BobCodyBot;
import ru.multibot.bobcody.SQL.Entities.Guest;
import ru.multibot.bobcody.SQL.Entities.Quote;
import ru.multibot.bobcody.SQL.Servies.QuoteAbyssServiceImp;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "quotemaster")
public class QuoteAbyssHandler implements SimpleHandlerInterface {
    @Autowired
    QuoteAbyssServiceImp quoteAbyssServiceImp;
    @Autowired
    BobCodyBot bobCodyBot;


    private String addQuoteToAbyss(Message message) {
        String replay;
        String textQuote;
        textQuote = message.getText().substring(3);
        if (textQuote.length() == 0) {
            replay = message.getFrom().getUserName() + ", ты цитату то введи";

        } else if (textQuote.length() < 5000) {
            Quote quote = new Quote(new Guest(message.getFrom()).getUserID(),
                    Long.valueOf(message.getDate()),
                    textQuote);
            quoteAbyssServiceImp.add(quote);
            replay = "Записал. Цитата будет добавлена в хранилище после проверки модератором.";
            sendToModerator(message);
        } else {
            replay = "Длина цитаты ограничена 5000 символами. Запиши себе в блокнот, потом поржешь";
        }

        return replay;
    }

    //шлет сообщение мне в личку в случае добавления цитат в бездну
    private void sendToModerator(Message message) {
        String myPrivatChatID = "445682905";
        String textInputMessage = "пользователь " + message.getFrom().getUserName() +
                " добавил цитатку. \n" +
                "Quote ID в бездне: " + quoteAbyssServiceImp.getQuoteIdByDateAdded(message.getDate().longValue()) +
                "\nText:\n" +
                message.getText().substring(3, message.getText().length());
        try {
            bobCodyBot.execute(new SendMessage().setChatId(myPrivatChatID).setText(textInputMessage));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(addQuoteToAbyss(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!lw");
        commands.add("!дц");
        commands.add("!aq");
        return commands;
    }
}

