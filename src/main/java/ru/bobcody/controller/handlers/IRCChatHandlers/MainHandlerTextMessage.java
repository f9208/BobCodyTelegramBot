package ru.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.bobcody.Entities.InputMessage;
import ru.bobcody.Servies.InputMessageService;
import ru.bobcody.controller.BobCodyBot;
import ru.bobcody.Entities.Guest;
import ru.bobcody.Servies.GuestService;
import ru.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler.SlapHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Component
public class MainHandlerTextMessage {
    @Autowired
    BobCodyBot bobCodyBot;
    @Autowired
    GuestService guestService;
    @Autowired
    InputMessageService inputMessageService;
    @Autowired
    SlapHandler slapHandler;

    private Map<String, SimpleHandlerInterface> multiHandler = new HashMap<>();

    public MainHandlerTextMessage(List<SimpleHandlerInterface> handlers) {
        for (SimpleHandlerInterface iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                multiHandler.put(insideListOrder, iterHandler);
            }
        }
    }

    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String textMessage = inputMessage.getText().toLowerCase();
        User user = inputMessage.getFrom();

        if (user != null) {
            saveGuest(user);
        }

        saveInputMessage(inputMessage);

        if (multiHandler.containsKey(textMessage.split(" ")[0])) {
            result = multiHandler.get(textMessage.split(" ")[0]).handle(inputMessage);
            if (result != null && result.getChatId() == null)
                //если нашли какую то команду и обработчик к ней то на остальные условия становятся не важны
                return result.setChatId(inputMessage.getChatId());
        }

        if (touchBotName(textMessage)) {
            result = multiHandler.get("бот").handle(inputMessage);
        }

        if (textMessage.contains("amd ") //переделать на регулярное выражение.
                || textMessage.contains("амд ")
                || textMessage.endsWith(" амд")
                || textMessage.endsWith(" amd")) {
            result = multiHandler.get("amd").handle(inputMessage);
        }

        if (textMessage.equals("!ссылки") ||
                textMessage.equals(" !ссылки") ||
                (textMessage.equals("!ссылки "))) {
            result.setText("ссылки бабая: https://t.me/izhmain/107384");
        }

        if (result != null && result.getChatId() == null) result.setChatId(inputMessage.getChatId());

        return result;
    }

    private boolean touchBotName(String text) {
        boolean result = false;
        String[] singleWordArray = text.split("[{^?*+ .,$:;#%/|()]");
        for (String oneWOrd : singleWordArray) {
            if (oneWOrd.equals("бот") ||
                    oneWOrd.equals("bob") ||
                    oneWOrd.equals("bot") ||
                    oneWOrd.equals("b0t") ||
                    oneWOrd.equals("@bobcodybot") ||
                    oneWOrd.equals("боб") ||
                    oneWOrd.equals("бобу") ||
                    oneWOrd.equals("бобби") ||
                    oneWOrd.equals("b0b")
            ) result = true;
        }
        return result;
    }

    private void saveGuest(User user) {
        Guest guest = new Guest(user);
        guestService.add(guest);
    }

    private void saveInputMessage(Message message) {
        inputMessageService.save(new InputMessage(message));
    }
}


