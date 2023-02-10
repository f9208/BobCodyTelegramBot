package ru.bobcody.updates.handlers.chathandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.Handler;
import ru.bobcody.domain.Guest;
import ru.bobcody.services.GuestService;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.TextConstantHandler;

import java.util.List;

//@Component
public class SetCityHandler implements Handler {
    @Autowired
    private GuestService guestService;
    @Value("${city.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        long guestId = inputMessage.getFrom().getId();
        Guest currentGuest = guestService.getGuest(guestId);
        SendMessage replay = new SendMessage();
        StringBuilder prefixGuestName = new StringBuilder("@");
        if (currentGuest.getUserName() == null) {
            prefixGuestName.append(currentGuest.getFirstName());
        } else {
            prefixGuestName.append(currentGuest.getUserName());
        }
        if (getCity(inputMessage)) {
            replay.setText(String.format(TextConstantHandler.YOUR_WEATHER_CITY, prefixGuestName.toString(), currentGuest.getCityName()));
        } else {
            String newCityName = inputMessage.getText().replaceAll("\\s+", " ").substring(7);
            currentGuest.setCityName(newCityName);
//            guestService.add(currentGuest);
            replay.setText(String.format(TextConstantHandler.YOUR_WEATHER_CITY_UPDATED, prefixGuestName.toString(), newCityName));
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
