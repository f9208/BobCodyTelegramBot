package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.List;

@Component
public class MyChatIdHandler implements SimpleHandlerInterface {
    @Value("${id.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText("айдишник этого чата:  " + inputMessage.getChat().getId());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
