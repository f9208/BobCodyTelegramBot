package ru.bobcody.updates.handlers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.GetWeatherForecastCommand;
import ru.bobcody.services.GuestService;
import ru.bobcody.thirdpartyapi.openweathermap.domain.City;
import ru.bobcody.thirdpartyapi.openweathermap.domain.ForecastRow;
import ru.bobcody.thirdpartyapi.openweathermap.domain.WeatherForecast;
import ru.bobcody.updates.handlers.AbstractHandler;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class WeatherForecastHandler extends AbstractHandler {

    private final static String defaultCitiName = "Бобруйск";

    private Set<String> shortDirective;

    @Autowired
    private GuestService guestService;

    @PostConstruct
    private void init() {
        shortDirective = getOrderList()
                .stream()
                .filter(o -> o.length() == 2)
                .collect(Collectors.toSet());
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = super.handle(inputMessage);

        Integer replyMessageId = inputMessage.getMessageId();
        result.setReplyToMessageId(replyMessageId);

        return result;
    }

    @Override
    protected String getResponseTextMessage(Message message) {

        String requestCityName = extractCityName(message.getText());

        if (StringUtils.isEmpty(requestCityName)) {
            requestCityName = getGuestCityName(message.getFrom());
        }

        try {
            WeatherForecast weatherForecast =
                    executeCommand(new GetWeatherForecastCommand(requestCityName));

            City respondCity = weatherForecast.getCity();

            int skipOver = 0;
            if (isShortDirective(message.getText())) {
                skipOver = 1;
            }

            return getCityInfo(respondCity) + getBody(weatherForecast.getList(), skipOver);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return String.format("город %s не найден", requestCityName);
            }
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                return "токен для WeatherApi все, вышел";
            }
            if (e.getStatusCode().is5xxServerError()) {
                return "Сервис погоды дезертировал";
            } else throw e;
        }
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getForecast();
    }

    private boolean isShortDirective(String textMessage) {
        String directive = StringUtils.substringBefore(textMessage, " ");
        return shortDirective.contains(directive);
    }

    private String extractCityName(String message) {

        String[] words = message
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .split(" ");

        if (words.length > 1) {
            return Arrays.stream(words).skip(1).limit(3).collect(Collectors.joining(" "));
        } else {
            return "";
        }
    }

    private String getGuestCityName(org.telegram.telegrambots.meta.api.objects.User user) {
        Long userId = user.getId();
        String cityName = guestService.getGuest(userId).getCityName();

        if (StringUtils.isNotEmpty(cityName)) {
            return cityName;
        }

        return defaultCitiName;
    }

    private String getCityInfo(City city) {
        return String.format("Прогноз погоды в г. %s, %s.\n%s\n",
                city.getName(),
                city.getCountry(),
                getSunRiseAndSet(city));
    }

    private String getBody(List<ForecastRow> rows, int skipOver) {
        int div = skipOver < 0 ? 0 : skipOver;

        return IntStream.range(0, 8)
                .filter(n -> n % (div + 1) == 0)
                .mapToObj(rows::get)
                .map(ForecastRow::toString)
                .collect(Collectors.joining("\n"));
    }

    //todo сделать по человечески
    private String getSunRiseAndSet(City city) {
        StringBuilder result = new StringBuilder();
        ZoneId utcZone = null;

        if (city.getTimezone() >= 0)
            utcZone = ZoneId.of("UTC+" + city.getTimezone() / 3600);
        else
            utcZone = ZoneId.of("UTC" + city.getTimezone() / 3600);

        Instant sunriseTime = Instant.ofEpochSecond(city.getSunrise());
        Instant sunsetTime = Instant.ofEpochSecond(city.getSunset());
        LocalDateTime sunrisePretty = LocalDateTime.ofInstant(sunriseTime, utcZone);
        LocalDateTime sunsetPretty = LocalDateTime.ofInstant(sunsetTime, utcZone);
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
