package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Getter
@Setter
public class TodayIsHandler implements SimpleHandlerInterface {
    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();

        TimeZone calliforniaTimeZone = TimeZone.getTimeZone("America/Los_Angeles");
        TimeZone bratislava = TimeZone.getTimeZone("Europe/Bratislava");

        StringBuilder currentDate = new StringBuilder();
        currentDate.append("Сегодня ")
                .append(LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL,
                        new Locale("ru", "RU")))
                .append(", ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        .withLocale(new Locale("ru", "RU"))))
                .append("\n")
                .append("время в Ижевске: ")
                .append(LocalDateTime.now(ZoneId.of("GMT+4")).format(DateTimeFormatter.ofPattern("HH:mm")))
                .append("\n")
                .append("в Дефолт-сити: ")
                .append(LocalDateTime.now(ZoneId.of("GMT+3")).format(DateTimeFormatter.ofPattern("HH:mm")))
                .append("\n")
                .append("у кориафчика в Словакии: ")
                .append(LocalDateTime.now(bratislava.toZoneId()).format(DateTimeFormatter.ofPattern("HH:mm")))
                .append("\n")
                .append("в солнечной Калифорнии: ")
                .append(LocalDateTime.now(calliforniaTimeZone.toZoneId()).format(DateTimeFormatter.ofPattern("HH:mm")))
                .append(" (")
                .append(LocalDateTime.now(calliforniaTimeZone.toZoneId()).format(DateTimeFormatter.ofPattern("dd MMMM")
                        .withLocale(new Locale("ru", "RU"))))
                .append(")\n");
        result.setText(currentDate.toString());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return Stream.of("!сегодня", "!дата", "!время", "!time", "!ща", "!now", "!time").collect(Collectors.toList());
    }
}
