package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.services.PieService;
import ru.bobcody.thirdPartyAPI.HotPies.PiesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class PiesHandler implements SimpleHandlerInterface {
    @Autowired
    PieService pieService;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        try {
            result.setText(pieService.getOneRandom().toString());
        } catch (Exception e) {
            e.printStackTrace();
            result.setText("че то с сервисом не то");
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!пирожок");
        commands.add("!pie");
        commands.add("pie");
        return commands;
    }
}
