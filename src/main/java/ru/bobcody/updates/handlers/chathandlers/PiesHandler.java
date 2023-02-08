package ru.bobcody.updates.handlers.chathandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.services.PieService;
import ru.bobcody.updates.handlers.AbstractHandler;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.TextConstantHandler;

import java.util.List;

@Slf4j
//@Component
public class PiesHandler extends AbstractHandler {
    @Autowired
    private PieService pieService;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        try {
            result.setText(pieService.getOne().toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("pie service has failure");
            result.setText(TextConstantHandler.PIE_SERVICE_FAILURE);
        }
        return result;
    }

    @Override
    protected String getResponseTextMessage(Message message) {
        return "null";
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getPies();
    }
}
