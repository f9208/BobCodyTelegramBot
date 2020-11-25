package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.Services.weather.OpenWeatherForecast;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:weatherProp.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "weather")
public class WeatherForecastHandler implements SimpleHandlerInterface {
    @Autowired
    OpenWeatherForecast openWeatherForecast;
    String defaultCityName;

    private String getForecast(String cityName) {
        String result = "123 а не погода.";
        try {
            if (cityName.equals("default")) {
                result = openWeatherForecast.getFullForecast(defaultCityName);
            } else {
                result = openWeatherForecast.getFullForecast(cityName);
            }
        } catch (IOException e) {
            result = cityName.replace("%20", " ") + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

    private String getShortForecast(String cityName) {
        String result = "123 а не погода.";
        System.out.println("пашет, ущуке!");
        try {
            if (cityName.equals("default")) {
                result = openWeatherForecast.getShortForecast(defaultCityName);
            } else {
                result = openWeatherForecast.getShortForecast(cityName);
            }
        } catch (IOException e) {
            result = cityName.replace("%20", " ") + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        return new SendMessage().setText(weatherForecastAnswer(inputMessage)).setReplyToMessageId(inputMessage.getMessageId());
    }

    private String weatherForecastAnswer(Message message) {
        StringBuilder cityName = new StringBuilder();
        String[] cityTwoWord = message.getText().split(" ");

        if (cityTwoWord.length == 1
                && (cityTwoWord[0].equals("!g") || cityTwoWord[0].equals("!w")
                || cityTwoWord[0].equals("!п"))) {
            cityName.append("default");
            return getShortForecast(cityName.toString());
        }
        System.out.println("название города: " + Arrays.toString(cityTwoWord));
        if (cityTwoWord.length == 1
                && (cityTwoWord[0].equals("!погода")||cityTwoWord[0].equals("!weather"))) {
            cityName.append("default");
            return getForecast(cityName.toString());
        }

        for (int i = 1; i < cityTwoWord.length; i++) {
            cityName.append(cityTwoWord[i]);
// вконце пробел не нужен
            if (i < cityTwoWord.length - 1) cityName.append("%20");
        }
        System.out.println(cityName);
// если !п, !w, !g - то выводим "короткую" погоду. если !погода- то длинную версию
        if (cityName.length() != 0 && (cityTwoWord[0].equals("!g") || cityTwoWord[0].equals("!w")
                || cityTwoWord[0].equals("!п"))) {
            return getShortForecast(cityName.toString());
        } else if (cityName.length() != 0 && (cityTwoWord[0].equals("!погода")||cityTwoWord[0].equals("!weather"))) {
            return getForecast(cityName.toString());

        } else return null;
    }

    @Override
    public List<String> getOrderList() {
        List<String> result = new ArrayList<>();
        result.add("!п");
        result.add("!w");
        result.add("!погода");
        result.add("!g");
        result.add("!weather");
        return result;
    }
}
