package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.thirdPartyAPI.weather.OpenWeatherForecast;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@Getter
@Setter
@PropertySource(value = "classpath:weatherProp.properties", encoding = "UTF-8")
public class WeatherForecastHandler implements SimpleHandlerInterface {
    @Autowired
    OpenWeatherForecast openWeatherForecast;
    @Value("${weather.defaultCityName}")
    String defaultCityName;
    @Value("${weather.command}")
    private List<String> commands;

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
            result = "default".equals(cityName)
                    ? openWeatherForecast.getFullForecast(defaultCityName)
                    : openWeatherForecast.getFullForecast(cityName);
        } catch (IOException e) {
            result = cityName.replace("%20", " ") + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

    private String getShortForecast(String cityName) {
        log.info("try to get short weather forecast for city: {}", cityName);
        String result = "непогода.";
        try {
            result = "default".equals(cityName)
                    ? openWeatherForecast.getShortForecast(defaultCityName)
                    : openWeatherForecast.getShortForecast(cityName);
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
        StringBuilder cityName = new StringBuilder();
        String[] cityTwoWord = message.getText().toLowerCase().split(" ");
        if (cityTwoWord.length == 1
                // если !п, !w, !g - то выводим "короткую" погоду.
                && (cityTwoWord[0].equals("!g") || cityTwoWord[0].equals("!w")
                || cityTwoWord[0].equals("!п"))) {
            cityName.append("default");
            return getShortForecast(cityName.toString());
        }
        //если !погода- то длинную версию
        if (cityTwoWord.length == 1
                && (cityTwoWord[0].equals("!погода") || cityTwoWord[0].equals("!weather"))) {
            cityName.append("default");
            return getForecast(cityName.toString());
        }

        for (int i = 1; i < cityTwoWord.length; i++) {
            cityName.append(cityTwoWord[i]);
// вконце пробел не нужен
            if (i < cityTwoWord.length - 1) cityName.append("%20");
        }
        log.info("city with multi word name: {}", cityName);
        if (cityName.length() != 0 && (cityTwoWord[0].equals("!g") || cityTwoWord[0].equals("!w")
                || cityTwoWord[0].equals("!п"))) {
            return getShortForecast(cityName.toString());
        } else if (cityName.length() != 0 && (cityTwoWord[0].equals("!погода") || cityTwoWord[0].equals("!weather"))) {
            return getForecast(cityName.toString());
        } else return null;
    }
}
