package ru.bobcody.updates.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.chathandlers.IHandler;
import ru.bobcody.services.PieService;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler;

import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class PiesHandler implements IHandler {
    private final PieService pieService;
    @Value("${pies.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        try {
            result.setText(pieService.getOne().toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("pie service failure");
            result.setText(TextConstantHandler.PIE_SERVICE_FAILURE);
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
