package ru.multibot.bobcody.ThirdPartyAPI.weather;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.multibot.bobcody.ThirdPartyAPI.weather.weatherCondition.City;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Component
@Configuration
@ConfigurationProperties(prefix = "weather")
@PropertySource("classpath:weatherProp.properties")
public class OpenWeatherForecast {
    @Getter
    @Setter
    String keyweatherapi;
    @Getter
    @Setter
    String units;
    @Getter
    @Setter
    String language;
    @Getter
    @Setter
    private String link; // = "https://api.openweathermap.org/data/2.5/forecast?";//q=Ижевск&appid=88da851375b3a80f6b0d65e55bfc2e37&units=metric";


    public String getFullForecast(String cityName) throws IOException {
        HeadWeather hw = getHeadWeather(cityName);
        StringBuilder result = new StringBuilder();
        result.append("Прогноз погоды в ").append(hw.getCity().getName()).append(", ");
        result.append(hw.getCity().getCountry()).append(". ");
        result.append(getSunRiseAndSet(hw.getCity())).append("\n").append("\n");
        List<FixedHourWeather> fwList = hw.getList();
        for (int i = 0; i < 8; i++) {
            result.append(fwList.get(i).asWeatherInCurrentCity(hw.getCity())).append("\n");
        }
        return result.toString();
    }

    public String getShortForecast(String cityName) throws IOException {
        HeadWeather hw = getHeadWeather(cityName);
        StringBuilder result = new StringBuilder();
        result.append("Прогноз погоды в ").append(hw.getCity().getName()).append(", ");
        result.append(hw.getCity().getCountry()).append(". ");
        result.append(getSunRiseAndSet(hw.getCity())).append("\n").append("\n");
        List<FixedHourWeather> fwList = hw.getList();
        for (int i = 0; i < 8; i = i + 2) {
            result.append(fwList.get(i).asWeatherInCurrentCity(hw.getCity())).append("\n");
        }
        return result.toString();

    }

    private URL createRequestLinkURL(String cityName) {
        URL requestLinkURL = null;
        try {
            requestLinkURL = new URL(link + "q=" + cityName + "&appid=" + keyweatherapi + units + language);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestLinkURL;
    }

    private HeadWeather getHeadWeather(String cityName) throws IOException {
        HeadWeather result = new HeadWeather();
        URL requestUrl = createRequestLinkURL(cityName);
        ObjectMapper openMapper = new ObjectMapper();
        JsonNode jsonNodeFullLine = openMapper.readTree(requestUrl);
        result = openMapper.readValue(jsonNodeFullLine.toString(), HeadWeather.class);

        return result;
    }

    private String getSunRiseAndSet(City currentCity) {
        StringBuilder result = new StringBuilder();
        ZoneId UTCZone = null;

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

}
