package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Component
@PropertySource(value = "classpath:onetwothree.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "onetwothree")
public class OneTwoThree implements SimpleHandlerInterface {

    List<String> phrases;

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
        List<String> commands = new ArrayList<>();
        commands.add("123");
        commands.add("!123");
        commands.add("!одиндватри");
        return commands;
    }
}
