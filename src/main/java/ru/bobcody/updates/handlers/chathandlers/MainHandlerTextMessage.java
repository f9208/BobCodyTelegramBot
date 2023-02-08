package ru.bobcody.updates.handlers.chathandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
//@Component
public class MainHandlerTextMessage {
    private final Map<String, IHandler> multiHandler = new HashMap<>();
    private final FlyHandler flyHandler;

    public MainHandlerTextMessage(List<IHandler> handlers, FlyHandler flyHandler) {
        this.flyHandler = flyHandler;
        for (IHandler iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                multiHandler.put(insideListOrder, iterHandler);
            }
        }
    }

    public SendMessage handle(Message message) {
        SendMessage result = new SendMessage();
        String flayer = flyHandler.findCommandInside(message);

        String textMessage = message.getText().toLowerCase();
        if (multiHandler.containsKey(textMessage.split(" ")[0])) {
            result = multiHandler.get(textMessage.split(" ")[0]).handle(message);
        } else if (!flayer.isEmpty()) {
            result.setText(flayer);
        }
        result.setChatId(message.getChatId().toString());
        return result;
    }
}