package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.List;

@Component
@Getter
@Setter
public class AmdSucksHandler implements SimpleHandlerInterface {
    @Value("${amd.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        int temp = Double.valueOf(Math.random() * 4).intValue();
        if (temp >> 1 == 1)
            result.setText("@" + inputMessage.getFrom().getUserName() + ", AMD сосет");
        else result.setText("@" + inputMessage.getFrom().getUserName() + ", AMD форева!");
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}


