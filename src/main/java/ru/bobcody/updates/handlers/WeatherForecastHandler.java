package ru.bobcody.updates.handlers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.ForceSendMessageCommand;
import ru.bobcody.command.GetWeatherForecastCommand;
import ru.bobcody.services.GuestService;
import ru.bobcody.services.SettingService;
import ru.bobcody.thirdpartyapi.openweathermap.domain.City;
import ru.bobcody.thirdpartyapi.openweathermap.domain.ForecastRow;
import ru.bobcody.thirdpartyapi.openweathermap.domain.WeatherForecast;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class WeatherForecastHandler extends AbstractHandler {

    private Set<String> shortDirective;

    @Autowired
    private GuestService guestService;

    @Autowired
    private SettingService settingService;

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

        String requestCityName = getParameterOverDirective(message.getText(), 3);

        if (StringUtils.isEmpty(requestCityName)) {
            requestCityName = guestService.getGuestCityName(message.getFrom().getId());
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

            e.printStackTrace();

            if (e.getStatusCode().equals(NOT_FOUND)) {
                return String.format("город %s не найден", requestCityName);
            }

            if (e.getStatusCode().equals(UNAUTHORIZED)) {

                notifyAdmin(Arrays.stream(e.getStackTrace())
                        .limit(15)
                        .map(s -> s.toString())
                        .collect(Collectors.joining("")));

                return "токен для WeatherApi все, вышел";
            }

            if (e.getStatusCode().is5xxServerError()) {

                notifyAdmin(Arrays.stream(e.getStackTrace())
                        .limit(15)
                        .map(s -> s.toString())
                        .collect(Collectors.joining("")));

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

    private void notifyAdmin(String message) {
        executeCommand(new ForceSendMessageCommand(settingService.getAdminChatId(),
                message));
    }
}
