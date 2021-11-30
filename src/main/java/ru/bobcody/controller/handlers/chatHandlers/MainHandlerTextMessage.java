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
        if (result.getChatId() == null) result.setChatId(message.getChatId().toString());

        String textMessage = message.getText().toLowerCase();
        if (multiHandler.containsKey(textMessage.split(" ")[0])) {
            result = multiHandler.get(textMessage.split(" ")[0]).handle(message);
            if (result.getChatId() == null) result.setChatId(message.getChatId().toString());
            return result;
        }
        String slapAnswer;
        if (!(slapAnswer = findCommandInside(message)).isEmpty()) {
            result.setText(slapAnswer);
            return result;
        }

        if ("!ссылки".equals(textMessage) ||
                " !ссылки".equals(textMessage) ||
                "!ссылки ".equals(textMessage)) {
            result.setText("ссылки бабая: https://t.me/izhmain/107384");
        }
        return result;
    }

    /**
     * Поиск команд обычно идет в мапе multiHandler, ключом к которой есть первое слово сообщения
     * но чтобы включить слапы бота и реакции на "амд" - надо пробежаться по всем словам сообщения,
     * и отреагировать в случае нахождения слов-команд
     */
    private String findCommandInside(Message message) { //todo слабое место эти иквалы
        String[] singleWordArray = message.getText().split("[{^?!*+ .,$:;#%/|()]");
        for (String oneWOrd : singleWordArray) {
            if ("бот".equals(oneWOrd) ||
                    "bob".equals(oneWOrd) ||
                    "bot".equals(oneWOrd) ||
                    "@bobcodybot".equals(oneWOrd) ||
                    "bobcodybot".equals(oneWOrd) ||
                    "боб".equals(oneWOrd) ||
                    "бобу".equals(oneWOrd) ||
                    "боту".equals(oneWOrd) ||
                    "бобби".equals(oneWOrd)
            ) return multiHandler.get("бот").handle(message).getText();
            if ("amd".equals(oneWOrd) || "амд".equals(oneWOrd)) {
                return multiHandler.get("amd").handle(message).getText();
            }
        }
        return "";
    }
}