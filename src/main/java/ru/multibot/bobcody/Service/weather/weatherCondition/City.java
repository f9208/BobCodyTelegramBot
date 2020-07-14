package ru.multibot.bobcody.Service.weather.weatherCondition;

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

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public Coord getCoord() {
        return coord;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getPopulation() {
        return population;
    }

    public int getTimezone() {
        return timezone;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
