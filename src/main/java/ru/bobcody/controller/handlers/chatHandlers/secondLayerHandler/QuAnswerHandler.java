package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.IHandler;

import java.util.List;
import java.util.Random;

@Component
@PropertySource(value = "classpath:answers/touchBot.properties", encoding = "UTF-8")
public class QuAnswerHandler implements IHandler {
    @Value("#{${qu.answer}}")
    private List<String> answer;
    @Value("${qu.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String sendText = "123";
        if (inputMessage.getFrom().getUserName() == null && inputMessage.getFrom().getUserName().equals("null")) {
            sendText = "@" + inputMessage.getFrom().getFirstName()
                    + ", " + answer.get(new Random().nextInt(answer.size()));
        } else
            sendText = "@" + inputMessage.getFrom().getUserName() + ", " + answer.get(new Random().nextInt(answer.size()));
        result.setText(sendText);
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
