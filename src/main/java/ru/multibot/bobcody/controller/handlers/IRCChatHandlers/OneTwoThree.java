package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Getter
@Setter
@Component
@PropertySource(value = "classpath:onetwothree.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "onetwothree")
public class OneTwoThree {

    List<String> phrases;

    public String getRandomPhrase() {
        Random r=new Random();
        return phrases.get(r.nextInt(phrases.size()));
    }
}
