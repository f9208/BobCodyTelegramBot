package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.IHandler;
import ru.bobcody.data.entities.Guest;
import ru.bobcody.data.services.GuestService;

import java.util.List;

import static ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.YOUR_WEATHER_CITY;
import static ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.YOUR_WEATHER_CITY_UPDATED;

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
        StringBuilder prefixGuestName = new StringBuilder("@");
        if (currentGuest.getUserName() == null) {
            prefixGuestName.append(currentGuest.getFirstName());
        } else {
            prefixGuestName.append(currentGuest.getUserName());
        }
        if (getCity(inputMessage)) {
            replay.setText(String.format(YOUR_WEATHER_CITY, prefixGuestName.toString(), currentGuest.getCityName()));
        } else {
            String newCityName = inputMessage.getText().replaceAll("\\s+", " ").substring(7);
            currentGuest.setCityName(newCityName);
            guestService.add(currentGuest);
            replay.setText(String.format(YOUR_WEATHER_CITY_UPDATED, prefixGuestName.toString(), newCityName));
        }
        return replay;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private boolean getCity(Message message) {
        String req = message.getText().trim();
        return commands.stream().anyMatch(a -> a.equals(req));
    }
}
