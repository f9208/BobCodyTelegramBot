package ru.multibot.bobcody.ThirdPartyAPI.weather;

import lombok.Getter;
import lombok.Setter;
import ru.multibot.bobcody.ThirdPartyAPI.weather.weatherCondition.City;

import java.util.List;

public class HeadWeather {
    @Getter
    @Setter
    int cod;
    @Getter
    @Setter
    int message;
    @Getter
    @Setter
    int cnt;
    @Getter
    @Setter
    List<FixedHourWeather> list;
    @Getter
    @Setter
    City city;
}
