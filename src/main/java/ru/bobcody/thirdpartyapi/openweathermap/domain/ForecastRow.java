package ru.bobcody.thirdpartyapi.openweathermap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@JsonIgnoreProperties(value = {"sys", "pop", "dt", "visibility", "clouds"})
public class ForecastRow {
    private static final String DEGREE_CELSIUS_CHAR = "\u2103";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("y-M-d H:m:s");
    private static final DateTimeFormatter DAY_HOUR_MINUTE_FORMATTER = DateTimeFormatter.ofPattern("EE, H:mm");

    @Getter
    @Setter
    private Main main;
    @Getter
    @Setter
    private WeatherShort[] weather;
    @Setter
    @Getter
    private Wind wind;
    @Setter
    @Getter
    private Rain rain;
    @Getter
    @JsonProperty("dt_txt")
    private LocalDateTime dateTime;
    @Getter
    @Setter
    private Snow snow;

    @Override
    public String toString() {
        return String.format("[%s]: t %s%s, %s, %s, %s%s",
                getDayHourMinute(),
                main.getTemperature(),
                DEGREE_CELSIUS_CHAR,
                getHumanDescription(),
                getFallOut(),
                wind,
                getHorizontalBreak()
        );
    }

    private String getHorizontalBreak() {
        return dateTime.getHour() == 21 ? "\n" : "";
    }

    private String getDayHourMinute() {
        return dateTime.format(DAY_HOUR_MINUTE_FORMATTER);
    }

    private String getFallOut() {
        if (rain != null) {
            return rain.toString();
        } else if (snow != null) {
            return snow.toString();
        } else
            return "без осадков";
    }

    private String getHumanDescription() {
        return weather[0].getDescription();
    }

    public void setDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
}
