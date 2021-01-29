package ru.multibot.bobcody.ThirdPartyAPI.weather;

import lombok.Getter;
import lombok.Setter;

public class WeatherDescription {
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private String main;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private String icon;
}
