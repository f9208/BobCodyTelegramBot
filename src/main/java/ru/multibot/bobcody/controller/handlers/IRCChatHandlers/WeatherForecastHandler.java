package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.multibot.bobcody.Services.weather.OpenWeatherForecast;

import java.io.IOException;

@Component
@Getter
@Setter
@PropertySource(value = "classpath:weatherProp.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "weather")
public class WeatherForecastHandler {
    @Autowired
    OpenWeatherForecast openWeatherForecast;
    String defaultCityName;


    public String getForecast(String cityName) {
        String result = "123 а не погода.";
        try {
            if (cityName.equals("default")) {
                result = openWeatherForecast.getForecast(defaultCityName);
            } else {
                result = openWeatherForecast.getForecast(cityName);
            }
        } catch (IOException e) {
            result = cityName + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

    public String getShortForecast(String cityName) {
        String result = "123 а не погода.";
        try {
            if (cityName.equals("default")) {
                result = openWeatherForecast.getShortForecast(defaultCityName);
            } else {
                result = openWeatherForecast.getShortForecast(cityName);
            }
        } catch (IOException e) {
            result = cityName + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

}
