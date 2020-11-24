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

@Component
@Getter
@Setter
@PropertySource(value = "classpath:slapPhrases.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "slapanswer")
public class SlapHandler implements SimpleHandlerInterface {

    List<String> phrases;

    private String getRandomAnswer() {
        Random r = new Random();
        return phrases.get(r.nextInt(phrases.size()));
    }

    private String answerForSlap(Message inputMessage) {
        if (inputMessage.getFrom().getUserName() == null && inputMessage.getFrom().getUserName().equals("null")) {
            System.out.println("юзер без ника. проверим условие: " + inputMessage.getFrom().getUserName() == null);
            return "@" + inputMessage.getFrom().getFirstName() + ", " + getRandomAnswer();
        } else return "@" + inputMessage.getFrom().getUserName() + ", " + getRandomAnswer();

    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(answerForSlap(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("bot");
        commands.add("бот");
        commands.add("бобби");
        commands.add("bobcodybot");
        commands.add("@bobcodybot");
        commands.add("b0t");

        return commands;
    }
}
