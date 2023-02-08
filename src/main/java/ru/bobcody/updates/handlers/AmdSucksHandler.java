package ru.bobcody.updates.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.TextConstantHandler;

import java.util.List;

@Component
public class AmdSucksHandler extends AbstractHandler {

    @Override
    protected String getResponseTextMessage(Message inputMessage) {
        double even = Math.random() * 4;

        if ((int) even >> 1 == 1) {
            return "@" + inputMessage.getFrom().getUserName() + ", " + TextConstantHandler.AMD_SUCKS;
        } else {
            return "@" + inputMessage.getFrom().getUserName() + ", " + TextConstantHandler.AMD_FOREVER;
        }
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getAmd();
    }
}


