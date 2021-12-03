package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.services.GuestService;
import ru.bobcody.thirdPartyAPI.openWeatherMap.WeatherForecastProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@PropertySource(value = "classpath:weatherProp.properties", encoding = "UTF-8")
public class WeatherForecastHandler implements SimpleHandlerInterface {
    @Autowired
    private WeatherForecastProvider openWeatherWeatherForecastProvider;
    @Value("${weather.command}")
    private List<String> commands;
    @Autowired
    GuestService guestService;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(weatherForecastAnswer(inputMessage));
        result.setReplyToMessageId(inputMessage.getMessageId());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private String getForecast(String cityName) {
        log.info("try to get full weather forecast for city: {}", cityName);
        String result = "непогода";
        try {
            result = openWeatherWeatherForecastProvider.getFullForecast(cityName);
        } catch (IOException e) {
            result = cityName.replace("%20", " ") + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

    private String getShortForecast(String cityName) {
        log.info("try to get short weather forecast for city: {}", cityName);
        String result = "непогода.";
        try {
            result = openWeatherWeatherForecastProvider.getShortForecast(cityName);
        } catch (IOException e) {
            result = cityName.replace("%20", " ") + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

    /**
     * в названии города может быть больше одного слова.
     * для решения этой проблемы переводим название города в массив из строк
     */
    private String weatherForecastAnswer(Message message) {

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
            // в конце пробел не нужен
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
        List<String> shortCommands = commands.stream().filter((c) -> c.length() == 2).collect(Collectors.toList());
        return shortCommands.stream().filter((c) -> c.equals(message)).collect(Collectors.toSet()).size() > 0;
    }

    private boolean hasAskedFullForecast(String message) {
        List<String> fullCommands = commands.stream().filter((c) -> c.length() > 2).collect(Collectors.toList());
        return fullCommands.stream().filter((c) -> c.equals(message)).collect(Collectors.toSet()).size() > 0;
    }
}
