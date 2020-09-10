package ru.multibot.bobcody.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.handlers.InputTextMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/****
    спринг будет создавать имплементации интерфейса  InputTextMessageHandler
    (у нас это IRCMainHandlerTextMessage, для которого chatID содержится в листе под индексом 0),
    помеченные аннотацией @Component и закидывать в мапу shiva.
    В этой мапе мы будем искать подходящую имплементации
    для каждого чата в зависимости от chatId

 */
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
        Long chatID = message.getChatId();
        SendMessage result = shiva.get(chatID).handle(message);

        return result;
    }
}