package ru.bobcody.thirdPartyAPI.openWeatherMap.weatherEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@JsonIgnoreProperties(value = {"sys", "pop", "dt", "visibility", "clouds"})
public class SingleRowForecast {
    @Getter
    @Setter
    @JsonProperty("main")
    private MainComponent mainComponent;
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

    private DateTimeFormatter dateTimeStamp = DateTimeFormatter.ofPattern("y-M-d H:m:s");

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[").append(dateTime.format(DateTimeFormatter.ofPattern("EE, H:mm"))).append("]:  ");
        result.append("t ").append(Math.round(mainComponent.getTemp())).append("\u2103, ");

        result.append(weather[0].getDescription()).append(", ");

        if (rain != null) {
            result.append(rain.toString()).append(", ");
        } else if (snow != null) {
            result.append(snow.toString()).append(", ");
        } else
            result.append("без осадков").append(", ");
        result.append(wind);
        if (dateTime.getHour() == 21) result.append("\n");
        return result.toString();
    }

    public void setDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, dateTimeStamp);
    }
}