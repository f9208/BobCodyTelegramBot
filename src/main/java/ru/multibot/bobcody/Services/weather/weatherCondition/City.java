package ru.multibot.bobcody.Services.weather.weatherCondition;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class City {
    private Coord coord;
    private int id;
    private String name;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;
}
