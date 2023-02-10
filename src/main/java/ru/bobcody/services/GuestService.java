package ru.bobcody.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bobcody.domain.City;
import ru.bobcody.domain.Guest;
import ru.bobcody.repository.GuestRepository;

import javax.persistence.EntityNotFoundException;

import static ru.bobcody.CommonConstants.DEFAULT_WEATHER_CITY_NAME;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    public Guest getGuest(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Guest with id=%s not found", id)));
    }

    public String getGuestCityName(Long guestId) {

        Guest guest = getGuest(guestId);

        return extractGuestCityName(guest);
    }

    public String extractName(Guest guest) {
        return guest.getUserName() == null ? guest.getFirstName() : guest.getUserName();
    }

    public String extractGuestCityName(Guest guest) {
        City city = guest.getCity();

        if (city == null || StringUtils.isEmpty(city.getName())) {
            return DEFAULT_WEATHER_CITY_NAME;
        } else {
            return city.getName();
        }
    }
}
