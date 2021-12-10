package ru.bobcody.thirdPartyAPI.openWeatherMap.weatherEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@JsonIgnoreProperties(value = {"coord", "id", "population"})
@ToString
public class City {
    private String name;
    private String country;
    private int timezone;
    private long sunrise;
    private long sunset;
}