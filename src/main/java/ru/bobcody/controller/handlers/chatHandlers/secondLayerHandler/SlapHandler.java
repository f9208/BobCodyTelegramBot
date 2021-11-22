package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@Getter
@Setter
@PropertySource(value = "classpath:answers/touchBot.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "slap")
public class SlapHandler implements SimpleHandlerInterface {
    List<String> phrases;

    private String getRandomAnswer() {
        Random r = new Random();
        return phrases.get(r.nextInt(phrases.size()));
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
        return Stream.of("bot", "bobcodybot", "бот", "бобби", "@bobcodybot").collect(Collectors.toList());
    }
}
