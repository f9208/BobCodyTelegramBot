package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
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
public class amdSuckHandler implements SimpleHandlerInterface {
    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        int temp = new Random().nextInt(4);
        if (temp >> 1 == 1)
            result.setText("@" + inputMessage.getFrom().getUserName() + ", AMD сосет");
        else result.setText("AMD форева!");

        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("amd");
        commands.add("амд");
        return commands;
    }
}

