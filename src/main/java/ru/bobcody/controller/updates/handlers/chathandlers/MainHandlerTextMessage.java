package ru.bobcody.controller.updates.handlers.chathandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MainHandlerTextMessage {
    private final Map<String, IHandler> multiHandler = new HashMap<>();
    @Autowired
    FlyHandler flyHandler;

    public MainHandlerTextMessage(List<IHandler> handlers) {
        for (IHandler iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                multiHandler.put(insideListOrder, iterHandler);
            }
        }
    }

    public SendMessage handle(Message message) { //todo разобраться с айдишниками чатов
        SendMessage result = new SendMessage();
        result.setChatId(message.getChatId().toString());

        String textMessage = message.getText().toLowerCase();
        if (multiHandler.containsKey(textMessage.split(" ")[0])) {
            result = multiHandler.get(textMessage.split(" ")[0]).handle(message);
            if (result.getChatId() == null) result.setChatId(message.getChatId().toString());
            return result;
        }
        String noHandled = flyHandler.findCommandInside(message);
        if (!noHandled.isEmpty()) {
            result.setText(noHandled);
            return result;
        }
        return result;
    }
}