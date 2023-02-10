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

        String guestName;

        if (inputMessage.getFrom().getUserName() == null
                || "null" .equals(inputMessage.getFrom().getUserName())) {
            guestName = inputMessage.getFrom().getFirstName();
        } else {
            guestName = inputMessage.getFrom().getUserName();
        }

        String botOpinion;

        if ((int) even >> 1 == 1) {
            botOpinion = TextConstantHandler.AMD_SUCKS;
        } else {
            botOpinion = TextConstantHandler.AMD_FOREVER;
        }

        return String.format("@%s, %s", guestName, botOpinion);
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getAmd();
    }
}


