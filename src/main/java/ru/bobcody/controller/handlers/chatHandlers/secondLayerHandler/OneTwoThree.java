package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@Component
@PropertySource(value = "classpath:answers/onetwothree.properties", encoding = "UTF-8")
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
        return Stream.of("123", "!123", "!одиндватри").collect(Collectors.toList());
    }
}
