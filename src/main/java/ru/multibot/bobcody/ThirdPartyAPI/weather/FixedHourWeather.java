package ru.multibot.bobcody.ThirdPartyAPI.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.multibot.bobcody.ThirdPartyAPI.weather.weatherCondition.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties(value = {"pop", "sys"})
public class FixedHourWeather {
    @Getter
    @Setter
    private long dt;
    @Getter
    @Setter
    @JsonProperty("main")
    private MainComponentForecast mainComponentForecast;
    @Getter
    @Setter
    @JsonProperty("weather")
    private WeatherDescription[] weatherDescription;
    @Getter
    @Setter
    private Clouds clouds;
    @Getter
    @Setter
    private Wind wind;
    @Getter
    @Setter
    private Rain rain;
    @Getter
    @JsonProperty("dt_txt")
    private LocalDateTime dateTime;
    @Getter
    @Setter
    private Snow snow;
    @Getter
    @Setter
    private int visibility;

    private static DateTimeFormatter inputDateTimeStamp = DateTimeFormatter.ofPattern("y-M-d H:m:s");
    private static DateTimeFormatter outputTimeStamp = DateTimeFormatter.ofPattern("EE, HH:mm");

    public void setDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, inputDateTimeStamp);
    }

    public String asWeatherInCurrentCity(City currentCity) {
        StringBuffer result = new StringBuffer();

        LocalDateTime ldt = LocalDateTime
                .ofInstant(Instant.ofEpochSecond(dt),
                        ZoneOffset.ofTotalSeconds(currentCity.getTimezone()));
        result.append("[")
                .append(ldt.format(outputTimeStamp)
                ).append("]: t ").append(mainComponentForecast.getTemp())
                .append("\u2103, ")
                .append(weatherDescription[0].getDescription()).append(", ");
        if (rain != null) {
            result.append(rain.toString()).append(", ");
        } else if (snow != null) {
            result.append(snow.toString()).append(", ");
        } else
            result.append("без осадков").append(", ");
        result.append(wind);


        return result.toString();
    }
}
