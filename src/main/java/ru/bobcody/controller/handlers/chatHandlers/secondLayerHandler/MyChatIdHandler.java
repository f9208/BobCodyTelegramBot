package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MyChatIdHandler implements SimpleHandlerInterface {
    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText("айдишник этого чата:" + inputMessage.getChat().getId());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return Stream.of("!id", "!айди").collect(Collectors.toList());
    }
}
