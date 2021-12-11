package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.IHandler;

import java.util.List;

@Component
@PropertySource(value = "classpath:answers/help.properties", encoding = "UTF-8")
public class HelpReplayHandler implements IHandler {
    @Value("${print.help}")
    private String helpAnswer;
    @Value("${help.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(helpAnswer);
        return sendMessage;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
