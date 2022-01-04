package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.IHandler;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@PropertySource(value = "classpath:answers/touchBot.properties", encoding = "UTF-8")
public class SlapHandler implements IHandler {
    @Value("#{${slap.phrases}}")
    private List<String> phrases;
    @Value("${slap.command}")
    private List<String> commands;
    private Random rand = new SecureRandom();

    private String getRandomAnswer() {
        return phrases.get(rand.nextInt(phrases.size()));
    }

    private String answerForSlap(Message inputMessage) {
        if (inputMessage.getFrom().getUserName() == null && inputMessage.getFrom().getUserName().equals("null")) {
            return "@" + inputMessage.getFrom().getFirstName() + ", " + getRandomAnswer();
        } else return "@" + inputMessage.getFrom().getUserName() + ", " + getRandomAnswer();
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        log.info("slap bot by {}", inputMessage.getText());
        SendMessage result = new SendMessage();
        result.setText(answerForSlap(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}