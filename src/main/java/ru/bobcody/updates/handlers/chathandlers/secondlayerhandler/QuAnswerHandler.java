package ru.bobcody.updates.handlers.chathandlers.secondlayerhandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.chathandlers.IHandler;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

//@Component
@PropertySource(value = "classpath:src/test/oldRes/answers/touchBot.properties", encoding = "UTF-8")
public class QuAnswerHandler implements IHandler {
    private final Random rand = new SecureRandom();
    @Value("#{${qu.answer}}")
    private List<String> answer;
    @Value("${qu.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String sendText;
        if (inputMessage.getFrom().getUserName() == null && inputMessage.getFrom().getUserName().equals("null")) {
            sendText = "@" + inputMessage.getFrom().getFirstName()
                    + ", " + answer.get(rand.nextInt(answer.size()));
        } else
            sendText = "@" + inputMessage.getFrom().getUserName() + ", " + answer.get(rand.nextInt(answer.size()));
        result.setText(sendText);
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
