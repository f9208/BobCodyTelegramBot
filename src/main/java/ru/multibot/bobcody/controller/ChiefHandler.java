package ru.multibot.bobcody.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.handlers.InputTextMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChiefHandler {
    private Map<Long, InputTextMessageHandler> shiva = new HashMap<>();

    public ChiefHandler(List<InputTextMessageHandler> handlers) {
        // лямбда-вариант.
        // handlers.forEach(handlerFather -> this.shiva.put(handlerFather.getState(), handlerFather));
        for (InputTextMessageHandler allTextMessageHandlers : handlers) {
            this.shiva.put(allTextMessageHandlers.getChatID(), allTextMessageHandlers);
        }
    }

    public SendMessage processInputMessage(Message message) {
        SendMessage result;
        Long chatID = message.getChatId();
        result = shiva.get(chatID).handle(message);
        return result;
    }
}