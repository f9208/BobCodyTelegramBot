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

@Component
@Getter
@Setter
@PropertySource(value = "classpath:answers/touchBot.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "qu")
public class QuAnswerHandler implements SimpleHandlerInterface {
    List<String> answer;

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
        List<String> commands = new ArrayList<>();
        commands.add("ку");
        commands.add("!ку");
        commands.add("qu");
        commands.add("!qu");
        return commands;
    }
}
