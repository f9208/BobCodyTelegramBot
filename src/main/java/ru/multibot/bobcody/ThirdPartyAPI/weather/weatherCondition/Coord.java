package ru.multibot.bobcody.ThirdPartyAPI.weather.weatherCondition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Coord {
    double lat;
    double lon;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (lat >= 0) result.append("N").append(lat).append("\u00B0, ");
        else result.append("S").append(Math.abs(lat)).append("\u00B0, ");

        if (lon >= 0) result.append("E").append(lon).append("\u00B0 ");
        else result.append("W").append(Math.abs(lon)).append("\u00B0 ");

        return result.toString();
    }

}
