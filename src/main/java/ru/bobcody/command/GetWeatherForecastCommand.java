package ru.bobcody.command;

import org.springframework.web.client.RestTemplate;
import ru.bobcody.thirdpartyapi.openweathermap.domain.WeatherForecast;

public class GetWeatherForecastCommand extends AbstractCommand {
    private String cityName;

    public GetWeatherForecastCommand(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public WeatherForecast execute() {

        RestTemplate restTemplate = new RestTemplate();

        String urlCity = getUrlByCityName();

        WeatherForecast weatherForecast = restTemplate.getForObject(urlCity, WeatherForecast.class);

        return weatherForecast;
    }

    private String getUrlByCityName() {
        return String.format("%s?q=%s&appid=%s&units=metric&lang=ru",
                settingService.getUrlWeatherApi(),
                cityName,
                settingService.getKeyWeatherApi());
    }
}
