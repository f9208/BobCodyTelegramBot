package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.data.services.GuestService;
import ru.bobcody.thirdpartyapi.openweathermap.WeatherForecastProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.CITY_NOT_FOUND;

@Slf4j
@Component
public class ForecastProducer {
    @Autowired
    private WeatherForecastProvider openWeatherWeatherForecastProvider;
    @Autowired
    private GuestService guestService;
    @Value("${weather.command}")
    private List<String> commands;

    /**
     * в названии города может быть больше одного слова.
     * для решения этой проблемы переводим название города в массив из строк
     */
    public String weatherForecastAnswer(Message message) {

        String[] cityTwoWord = message.getText().toLowerCase().split(" ");
        boolean askedShortForecast = hasAskedShotForecast(cityTwoWord[0]);
        boolean askedFullForecast = hasAskedFullForecast(cityTwoWord[0]);

        // если !п, !w, !g - то выводим "короткую" погоду.
        if (cityTwoWord.length == 1 && askedShortForecast) {
            String city = guestService.findById(message.getFrom().getId()).getCityName();
            return getShortForecast(city);
        }

        //если !погода, !weather - то длинную версию
        if (cityTwoWord.length == 1 && askedFullForecast) {
            String city = guestService.findById(message.getFrom().getId()).getCityName();
            return getForecast(city);
        }

        StringBuilder cityName = new StringBuilder();
        for (int i = 1; i < cityTwoWord.length; i++) {
            cityName.append(cityTwoWord[i]);
            if (i < cityTwoWord.length - 1) cityName.append("%20");
        }
        log.info("city with multi word name: {}", cityName);
        if (cityName.length() != 0 && askedShortForecast) {
            return getShortForecast(cityName.toString());
        } else if (cityName.length() != 0 && askedFullForecast) {
            return getForecast(cityName.toString());
        } else return null;
    }

    private boolean hasAskedShotForecast(String message) {
        List<String> shortCommands = commands.stream().filter(c -> c.length() == 2).collect(Collectors.toList());
        return shortCommands.stream().anyMatch(c -> c.equals(message));
    }

    private boolean hasAskedFullForecast(String message) {
        List<String> fullCommands = commands.stream().filter(c -> c.length() > 2).collect(Collectors.toList());
        return fullCommands.stream().anyMatch(c -> c.equals(message));
    }

    private String getForecast(String cityName) {
        log.info("try to get full weather forecast for city: {}", cityName);
        String result = "непогода";
        try {
            result = openWeatherWeatherForecastProvider.getFullForecast(cityName);
        } catch (IOException e) {
            result = cityName.replace("%20", " ") + CITY_NOT_FOUND;
        }
        return result;
    }

    private String getShortForecast(String cityName) {
        log.info("try to get short weather forecast for city: {}", cityName);
        String result = "непогода.";
        try {
            result = openWeatherWeatherForecastProvider.getShortForecast(cityName);
        } catch (IOException e) {
            result = cityName.replace("%20", " ") + CITY_NOT_FOUND;
        }
        return result;
    }
}
