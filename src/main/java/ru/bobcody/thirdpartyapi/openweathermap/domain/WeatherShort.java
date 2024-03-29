package ru.bobcody.thirdpartyapi.openweathermap.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WeatherShort {
    private int id;
    private String main;
    private String description;
    private String icon;
}