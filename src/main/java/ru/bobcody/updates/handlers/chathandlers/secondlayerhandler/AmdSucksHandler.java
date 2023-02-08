package ru.bobcody.updates.handlers.chathandlers.secondlayerhandler;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.chathandlers.IHandler;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler;

import java.util.List;

//@Component
public class AmdSucksHandler implements IHandler {
    @Value("${amd.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        double temp = Math.random() * 4;
        if ((int) temp >> 1 == 1)
            result.setText("@" + inputMessage.getFrom().getUserName() + ", " + TextConstantHandler.AMD_SUCKS);
        else result.setText("@" + inputMessage.getFrom().getUserName() + ", " + TextConstantHandler.AMD_FOREVER);
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}


