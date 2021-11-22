package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.List;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:answers/help.properties", encoding = "UTF-8")
public class HelpReplayHandler implements SimpleHandlerInterface {
    @Value("${print.help}")
    String helpAnswer;
    @Value("${help.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(getHelpAnswer());
        return sendMessage;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
