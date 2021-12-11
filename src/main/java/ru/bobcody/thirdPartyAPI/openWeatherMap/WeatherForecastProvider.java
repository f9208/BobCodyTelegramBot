package ru.bobcody.thirdPartyAPI.openWeatherMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.bobcody.thirdPartyAPI.openWeatherMap.weatherEntity.City;
import ru.bobcody.thirdPartyAPI.openWeatherMap.weatherEntity.SingleRowForecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherForecastProvider {
    @Value("${weather.link}")
    private String link;
    @Value("${weather.units}")
    private String units;
    @Value("${weather.keyweatherapi}")
    private String keyweatherapi;
    @Getter
    @Value("${weather.language}")
    private String language;
    @Autowired
    private ObjectMapper objectMapper;

    public String getFullForecast(String cityName) throws IOException {
        return fullOrShortResolver(cityName, true);
    }

    public String getShortForecast(String cityName) throws IOException {
        return fullOrShortResolver(cityName, false);
    }

    private String fullOrShortResolver(String cityName, boolean full) throws IOException {
        URL urlToGetForecast = prepareUrl(cityName);
        String fullForecastAsJson = readUrl(urlToGetForecast);
        JsonNode baseJsonNode = objectMapper.readTree(fullForecastAsJson);

        City city = getCity(baseJsonNode.get("city"));
        StringBuilder result = new StringBuilder();
        result.append(header(city));

        List<SingleRowForecast> forecast = readForecast(baseJsonNode.get("list"));
        if (full) {
            result.append(bodyFull(forecast));
        } else {
            result.append(bodyShort(forecast));
        }
        return result.toString();
    }

    public URL prepareUrl(String cityName) throws MalformedURLException {
        return new URL(link + "q=" + cityName + "&appid=" + keyweatherapi + units + language);
    }

    private String readUrl(URL link) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));
        StringBuilder result = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            result.append(temp);
        }
        br.close();
        return result.toString();
    }

    private City getCity(JsonNode jsonNode) throws JsonProcessingException {
        return objectMapper.readValue(jsonNode.toString(), City.class);
    }

    private String prepareSunRiseAndSet(City city) {
        StringBuilder result = new StringBuilder();
        ZoneId UTCZone = null;

        if (city.getTimezone() >= 0)
            UTCZone = ZoneId.of("UTC+" + city.getTimezone() / 3600);
        else
            UTCZone = ZoneId.of("UTC" + city.getTimezone() / 3600);

        Instant sunriseTime = Instant.ofEpochSecond(city.getSunrise());
        Instant sunsetTime = Instant.ofEpochSecond(city.getSunset());
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

    private String header(City city) {
        StringBuilder result = new StringBuilder();
        result.append("Прогноз погоды в ").append(city.getName()).append(", ");
        result.append(city.getCountry()).append(". ");
        result.append(prepareSunRiseAndSet(city)).append("\n").append("\n");
        return result.toString();
    }

    private String bodyFull(List<SingleRowForecast> rows) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            result.append(rows.get(i)).append("\n");
        }
        return result.toString();
    }

    private String bodyShort(List<SingleRowForecast> rows) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i = i + 2) {
            result.append(rows.get(i)).append("\n");
        }
        return result.toString();
    }

    private List<SingleRowForecast> readForecast(JsonNode jsonNode) throws JsonProcessingException {
        List<SingleRowForecast> result = new ArrayList<>();
        SingleRowForecast oneRow;
        for (int i = 0; i < 10; i++) {
            oneRow = objectMapper.readValue(jsonNode.get(i).toString(), SingleRowForecast.class);
            result.add(oneRow);
        }
        return result;
    }
}
