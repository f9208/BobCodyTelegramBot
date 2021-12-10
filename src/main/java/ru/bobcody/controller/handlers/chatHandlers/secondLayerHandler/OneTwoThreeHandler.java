package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.List;
import java.util.Random;

@Component
@PropertySource(value = "classpath:answers/onetwothree.properties", encoding = "UTF-8")
public class OneTwoThreeHandler implements SimpleHandlerInterface {
    @Value("#{${onetwothree.phrases}}")
    private List<String> phrases;
    @Value("${onetwothree.command}")
    private List<String> commands;

    private String getRandomPhrase() {
        Random r = new Random();
        return phrases.get(r.nextInt(phrases.size()));
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(getRandomPhrase());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
