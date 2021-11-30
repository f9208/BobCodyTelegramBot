package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.services.PieService;

import java.util.List;

@Slf4j
@Component
@Getter
@Setter
public class PiesHandler implements SimpleHandlerInterface {
    @Autowired
    PieService pieService;
    //оставил через статическое для примера
    private static List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        try {
            result.setText(pieService.getOne().toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("pie service failure");
            result.setText("че то с сервисом пирожков не то");
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    @Autowired
    public void setOrders(@Value("${pies.command}") List<String> commands) {
        PiesHandler.commands = commands;
    }
}
