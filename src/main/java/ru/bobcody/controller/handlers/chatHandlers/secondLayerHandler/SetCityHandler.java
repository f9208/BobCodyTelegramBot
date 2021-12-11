package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.IHandler;
import ru.bobcody.entities.Guest;
import ru.bobcody.services.GuestService;

import java.util.List;

@Component
public class SetCityHandler implements IHandler {
    @Autowired
    private GuestService guestService;
    @Value("${city.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        long guestId = inputMessage.getFrom().getId();
        Guest currentGuest = guestService.findById(guestId);
        SendMessage replay = new SendMessage();
        StringBuilder prefix = new StringBuilder("@");
        if (currentGuest.getUserName() == null) {
            prefix.append(currentGuest.getFirstName());
        } else {
            prefix.append(currentGuest.getUserName());
        }
        if (getCity(inputMessage)) {
            replay.setText(prefix.toString() + ", ваш дефолтный город - " + currentGuest.getCityName());
        } else {
            String newCityName = inputMessage.getText().replaceAll("\\s+", " ").substring(7);
            currentGuest.setCityName(newCityName);
            guestService.add(currentGuest);

            replay.setText(prefix.toString() + ", теперь твой погодный город - " + newCityName);
        }
        return replay;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private boolean getCity(Message message) {
        String req = message.getText().trim();
        return commands.stream().anyMatch((a) -> a.equals(req));
    }
}
