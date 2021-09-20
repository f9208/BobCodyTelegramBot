package ru.bobcody.controller.handlers.chatHandlers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Setter
@Getter
@Component
public class MainHandlerTextMessage {
    private Map<String, SimpleHandlerInterface> multiHandler = new HashMap<>();

    public MainHandlerTextMessage(List<SimpleHandlerInterface> handlers) {
        for (SimpleHandlerInterface iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                multiHandler.put(insideListOrder, iterHandler);
            }
        }
    }

    public SendMessage handle(Message message) {
        SendMessage result = new SendMessage();
        if (result.getChatId() == null) result.setChatId(message.getChatId());

        String textMessage = message.getText().toLowerCase();
        if (multiHandler.containsKey(textMessage.split(" ")[0])) {
            result = multiHandler.get(textMessage.split(" ")[0]).handle(message);
            if (result.getChatId() == null) result.setChatId(message.getChatId());
            return result;
        }

        if (touchBotName(textMessage)) {
            result = multiHandler.get("бот").handle(message);
        }

        if (textMessage.contains("amd ") //todo переделать на регулярное выражение.
                || textMessage.contains("амд ")
                || textMessage.endsWith(" амд")
                || textMessage.endsWith(" amd")) {
            result = multiHandler.get("amd").handle(message);
        }

        if (textMessage.equals("!ссылки") ||
                textMessage.equals(" !ссылки") ||
                (textMessage.equals("!ссылки "))) {
            result.setText("ссылки бабая: https://t.me/izhmain/107384");
        }
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
}