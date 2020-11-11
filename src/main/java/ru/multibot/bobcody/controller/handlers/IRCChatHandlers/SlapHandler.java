package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Random;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:slapPhrases.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "slapanswer")
public class SlapHandler {

    List<String> phrases;

    private String getRandomAnswer() {
        Random r = new Random();
        return phrases.get(r.nextInt(phrases.size()));
    }

    public String answerForSlap(Message inputMessage) {
        if (inputMessage.getFrom().getUserName() == null && inputMessage.getFrom().getUserName().equals("null")) {
            System.out.println("юзер без ника. проверим условие: " + inputMessage.getFrom().getUserName() == null);
            return "@" + inputMessage.getFrom().getFirstName() + ", " + getRandomAnswer();
        } else return "@" + inputMessage.getFrom().getUserName() + ", " + getRandomAnswer();

    }
}
