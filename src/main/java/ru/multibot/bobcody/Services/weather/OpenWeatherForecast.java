package ru.multibot.bobcody.Services.weather;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.multibot.bobcody.Services.weather.weatherCondition.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
@Configuration
@ConfigurationProperties(prefix = "weather")
@PropertySource("classpath:weatherProp.properties")
public class OpenWeatherForecast {
    @Setter
    @Getter
    private String cityName;
    @Setter
    @Getter
    private String link;
    @Setter
    @Getter
    private String units;
    @Setter
    @Getter
    private String keyweatherapi;
    @Getter
    @Setter
    private String language;
    @Getter
    private URL callFullForecastURL;
    @Getter
    private String fullForecastJson;
    @Getter
    public JsonNode jsonNodeFullLine;
    @Getter
    private ObjectMapper openMapper = new ObjectMapper();

    private City currentCity; // возможно сделать суперкласс "локация" и унаследовать в конечном итоге класс City от него. но это не точно

    public String getFullForecast(String cityName) throws IOException {
        setCityName(cityName);
        call5Days(cityName);
        // инициализация данных
        fullForecastJson = parserURL();
        jsonNodeFullLine = openMapper.readTree(fullForecastJson);

        StringBuilder result = new StringBuilder();
        //result.append("Прогноз погоды в ").append(cityName).append(", ");
        result.append("Прогноз погоды в ").append(getCurrentCity().getName()).append(", ");
        result.append(getCurrentCity().getCountry()).append(". ");
        result.append(getSunRiseAndSet()).append("\n").append("\n");

        List<FullHouse> forecast = forecastAsList();

        for (int i = 0; i < 8; i++) {
            forecast.get(i);
            result.append(forecast.get(i)).append("\n");
        }

        return result.toString();
    }

    public String getShortForecast(String cityName) throws IOException {
        setCityName(cityName);
        call5Days(cityName);
        // инициализация данных
        fullForecastJson = parserURL();
        jsonNodeFullLine = openMapper.readTree(fullForecastJson);

        StringBuilder result = new StringBuilder();
        //result.append("Прогноз погоды в ").append(cityName).append(", ");
        result.append("Прогноз погоды в ").append(getCurrentCity().getName()).append(", ");
        result.append(getCurrentCity().getCountry()).append(". ");
        result.append(getSunRiseAndSet()).append("\n").append("\n");

        List<FullHouse> forecast = forecastAsList();

        for (int i = 0; i < 8; i = i + 2) {
            forecast.get(i);
            result.append(forecast.get(i)).append("\n");
        }

        return result.toString();
    }


    private City getCurrentCity() throws IOException {
        currentCity = openMapper.readValue(getCityAsJson().toString(), City.class);
        return currentCity;
    }

    private String getSunRiseAndSet() throws IOException {
        StringBuilder result = new StringBuilder();
        ZoneId UTCZone = null;

        if (currentCity == null) getCurrentCity();
        if (currentCity.getTimezone() >= 0)
            UTCZone = ZoneId.of("UTC+" + String.valueOf(currentCity.getTimezone() / 3600));
        else
            UTCZone = ZoneId.of("UTC" + String.valueOf(currentCity.getTimezone() / 3600));

        Instant sunriseTime = Instant.ofEpochSecond(currentCity.getSunrise());
        Instant sunsetTime = Instant.ofEpochSecond(currentCity.getSunset());
        LocalDateTime sunrisePretty = LocalDateTime.ofInstant(sunriseTime, UTCZone);
        LocalDateTime sunsetPretty = LocalDateTime.ofInstant(sunsetTime, UTCZone);
        if (sunrisePretty.getHour() <= 9) {
            result.append("Рассвет: 0");
        } else {
            result.append("Рассвет: ");
        }

        result.append(sunrisePretty.getHour()).append(":");

        if (sunrisePretty.getMinute() <= 9) {
            result.append("0");
            result.append(sunrisePretty.getMinute());
        } else {
            result.append(sunrisePretty.getMinute());
        }

        if (sunsetPretty.getHour() <= 9) {
            result.append(", закат: 0");
            result.append(sunsetPretty.getHour()).append(":");
        } else {
            result.append(", закат: ");
            result.append(sunsetPretty.getHour()).append(":");
        }
        if (sunsetPretty.getMinute() <= 9) {
            result.append("0");
            result.append(sunsetPretty.getMinute());
        } else {
            result.append(sunsetPretty.getMinute());
        }
        return result.toString();
    }


    private JsonNode getCityAsJson() {
        return jsonNodeFullLine.get("city");
    }

    private List<FullHouse> forecastAsList() throws IOException {
        FullHouse fh;
        List<FullHouse> resultAsList = new ArrayList<>();
        JsonNode list = jsonNodeFullLine.get("list");

        //добавляем погоду лист
        for (int i = 0; i < 40; i++) {
            fh = openMapper.readValue(list.get(i).toString(), FullHouse.class);
            resultAsList.add(fh);
        }
        return resultAsList;
    }

    private String parserURL() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(callFullForecastURL.openStream()));
        StringBuilder result = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            result.append(temp);
        }
        br.close();
        return result.toString();
    }

    public void call5Days(String cityName) {
        try {
            this.callFullForecastURL = new URL(link + "q=" + cityName + "&appid=" + keyweatherapi + units + language);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}

@JsonIgnoreProperties(value = {"sys", "pop"})
class FullHouse {
    @Getter
    @Setter
    @JsonProperty("main")
    private MainComponentForecast mainComponentForecast;
    @Getter
    @Setter
    private WeatherShort[] weather;
    @Getter
    @Setter
    private Clouds clouds;
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
    private int dt;
    @Getter
    @Setter
    private Snow snow;
    @Getter
    @Setter
    private int visibility;
    @Getter
    @Setter
    private double pop;


    private DateTimeFormatter dateTimeStamp = DateTimeFormatter.ofPattern("y-M-d H:m:s");

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[").append(dateTime.format(DateTimeFormatter.ofPattern("EE, H:mm"))).append("]:  ");
        result.append("t ").append(Math.round(mainComponentForecast.getTemp())).append("\u2103, ");

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

class WeatherShort {
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
