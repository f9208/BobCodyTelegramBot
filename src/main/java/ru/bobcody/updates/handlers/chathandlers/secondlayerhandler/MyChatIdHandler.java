package ru.bobcody.updates.handlers.chathandlers.secondlayerhandler;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.chathandlers.IHandler;

import java.util.List;

import static ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.CURRENT_CHAT_ID;

//@Component
public class MyChatIdHandler implements IHandler {
    @Value("${id.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(String.format(CURRENT_CHAT_ID, inputMessage.getChat().getId()));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
