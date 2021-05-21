package ru.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.Servies.*;
import ru.bobcody.controller.BobCodyBot;
import ru.bobcody.Entities.Guest;
import ru.bobcody.Entities.QuoteEntityAbyss;
import ru.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "quotemaster")
public class QuoteAbyssHandler implements SimpleHandlerInterface {
    @Autowired
    QuoteAbyssService quoteAbyssService;
    @Autowired
    CapsQuoteStorageService capsQuoteStorageService;
    @Autowired
    QuoteStorageService quoteStorageService;

    @Autowired
    BobCodyBot bobCodyBot;

    private String addQuoteToAbyss(Message message) {
        String replay;
        String textQuote;
        textQuote = message.getText().substring(3);
        if (textQuote.length() == 0) {
            replay = message.getFrom().getUserName() + ", ты цитату то введи";

        } else if (textQuote.length() < 5000) {
            QuoteEntityAbyss quoteEntityAbyss = new QuoteEntityAbyss(new Guest(message.getFrom()),
                    Long.valueOf(message.getDate()),
                    textQuote);
            quoteAbyssService.add(quoteEntityAbyss);
            replay = "Записал в бездну. Цитата будет добавлена в хранилище после проверки модератором.";
            sendToModerator(message);
        } else {
            replay = "Длина цитаты ограничена 5000 символами. Запиши себе в блокнот, потом поржешь";
        }

        return replay;
    }

    //шлет сообщение мне в личку в случае добавления цитат в бездну
    private void sendToModerator(Message message) {
        String myPrivateChatID = "445682905";
        String textInputMessage = "пользователь " + message.getFrom().getUserName() +
                " добавил цитатку. \n" +
                "ID цитаты в бездне: " + quoteAbyssService.getQuoteIdByDateAdded(message.getDate().longValue()) +
                "\nText:\n" +
                message.getText().substring(3);
        try {
            bobCodyBot.execute(new SendMessage().setChatId(myPrivateChatID).setText(textInputMessage));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getText().trim().startsWith("!добавьк") && inputMessage.getChatId() == 445682905) {
            return result.setText(approveCaps(inputMessage));
        }
        if (inputMessage.getText().trim().startsWith("!добавьц") && inputMessage.getChatId() == 445682905) {
            return result.setText(approveQuote(inputMessage));
        }
        if (inputMessage.getText().trim().startsWith("!дц")
                || inputMessage.getText().trim().startsWith("!aq")
                || inputMessage.getText().trim().startsWith("!lw"))
            result.setText(addQuoteToAbyss(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {

        List<String> commands = new ArrayList<>();
        commands.add("!lw");
        commands.add("!LW");
        commands.add("!дц");
        commands.add("!ДЦ");
        commands.add("!aq");
        commands.add("!AQ");
        commands.add("!добавьц");
        commands.add("!Добавьц");
        commands.add("!добавьк");
        commands.add("!Добавьк");
        return commands;
    }

    private String approveCaps(Message inputMessage) {
        String result = "что то пошло не так";

        String textMessage = inputMessage.getText().toLowerCase();
        Long inputCapsQuoteIdFromAbyss;

        if (textMessage.split(" ").length == 1) return "чо добавить то?";
        if (textMessage.split(" ").length == 2) {
            try {
                inputCapsQuoteIdFromAbyss = Long.valueOf(textMessage.split(" ")[1]);
            } catch (NumberFormatException e) {
                return "цифры вводи.";
            } catch (ArrayIndexOutOfBoundsException e) {
                return "ой не влажу в массив";
            }

            if (!quoteAbyssService.containInAbyss(inputCapsQuoteIdFromAbyss))
                return "нет такого номера в бездне";

            if (capsQuoteStorageService.containInCapsQuoteStorage(inputCapsQuoteIdFromAbyss)) {
                return "ее уже добавляли в хранилище Капсов.";
            }
            if (quoteStorageService.containInQuoteStorage(inputCapsQuoteIdFromAbyss)) {
                return "ее уже добавляли в хранилище Цитат.";
            } else {
                Long resultNumber = quoteAbyssService.approveCaps(inputCapsQuoteIdFromAbyss);
                return "Капсик добавлен за номером " + resultNumber.longValue();
            }
        }
        return result;

    }

    private String approveQuote(Message inputMessage) {
        String result = "что то пошло не так";
        String textMessage = inputMessage.getText().toLowerCase();
        Long inputQuoteIdFromAbyss;

        if (textMessage.split(" ").length == 1) return "чо добавить то?";
        if (textMessage.split(" ").length == 2) {
            try {
                inputQuoteIdFromAbyss = Long.valueOf(textMessage.split(" ")[1]);
            } catch (NumberFormatException e) {
                return "цифры вводи.";
            } catch (ArrayIndexOutOfBoundsException e) {
                return "ой не влажу в массив";
            }

            if (!quoteAbyssService.containInAbyss(inputQuoteIdFromAbyss))
                return "нет такого номера в бездне";

            if (capsQuoteStorageService.containInCapsQuoteStorage(inputQuoteIdFromAbyss)) {
                return "ее уже добавляли в хранилище Капсов.";
            }
            if (quoteStorageService.containInQuoteStorage(inputQuoteIdFromAbyss)) {
                return "ее уже добавляли в хранилище Цитат.";

            } else {
                Long resultNumber = quoteAbyssService.approveQuote(inputQuoteIdFromAbyss);
                return "цитата добавленна за номером " + resultNumber.longValue();
            }
        }
        return result;
    }

}