package ru.multibot.bobcody.controller.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.multibot.bobcody.Service.weather.OpenWeatherForecast;

import java.io.IOException;

@Component
@Getter
@Setter
public class WeatherForecastHandler {
    @Autowired
    OpenWeatherForecast openWeatherForecast;

    public String getForecast(String cityName) {
        String result = "123 а не погода.";
        try {
            result = openWeatherForecast.getForecast(cityName);
        } catch (IOException e) {
            result = cityName + "? Где это? в Бельгии что-ли?";
        }
        return result;
    }

}
