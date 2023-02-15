package ru.bobcody.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.domain.City;
import ru.bobcody.domain.Guest;
import ru.bobcody.services.CityService;
import ru.bobcody.services.GuestService;

import java.util.List;

@Component
public class CityHandler extends AbstractHandler {
    @Autowired
    private GuestService guestService;

    @Autowired
    private CityService cityService;

    @Override
    protected String getResponseTextMessage(Message inputMessage) {

        String textMessage = inputMessage.getText();

        long guestId = inputMessage.getFrom().getId();
        Guest guest = guestService.getGuest(guestId);

        if (isShowCurrentCityDirective(textMessage)) {

            String cityName = guestService.extractGuestCityName(guest);
            String guestName = guestService.extractName(guest);

            return String.format("@%s, твой город по умолчанию - %s", guestName, cityName);
        }

        String cityName = getParameterOverDirective(textMessage, 3);

        City foundCity = cityService.getCityByName(cityName);

        guest.setCity(foundCity);

        return String.format("@%s, теперь твой погодный город - %s", guestService.extractName(guest), foundCity.getName());
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getCity();
    }

    //todo подумать над абстракций этой фичи
    private boolean isShowCurrentCityDirective(String textMessage) {
        return getOrderList().stream().anyMatch(a -> a.equals(textMessage.toLowerCase()));
    }
}
