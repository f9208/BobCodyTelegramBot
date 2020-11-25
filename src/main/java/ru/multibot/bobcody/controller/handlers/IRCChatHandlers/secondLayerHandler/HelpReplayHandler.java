package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:help.properties", encoding = "UTF-8")
public class HelpReplayHandler implements SimpleHandlerInterface {
    @Value("${print.help}")
    String helpAnswer;

    @Override
    public SendMessage handle(Message inputMessage) {
        return new SendMessage().setText(getHelpAnswer());
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!help");
        commands.add("!хелп");
        commands.add("!помощь");
        commands.add("!команды");
        commands.add("/start");
        return commands;
    }
}
