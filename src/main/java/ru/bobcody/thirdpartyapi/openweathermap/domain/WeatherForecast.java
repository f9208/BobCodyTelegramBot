package ru.bobcody.thirdpartyapi.openweathermap.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherForecast {
    String code;
    String message;
    String cnt;
    List<ForecastRow> list;
    City city;
}
