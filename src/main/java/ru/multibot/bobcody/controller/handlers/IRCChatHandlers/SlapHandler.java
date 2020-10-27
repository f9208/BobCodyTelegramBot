package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:slapPhrases.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "slapanswer")
public class SlapHandler {

    List<String> phrases;
    public String getRandomAnswer() {
        Random r=new Random();
        return phrases.get(r.nextInt(phrases.size()));
    }
}
