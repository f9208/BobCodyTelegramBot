package ru.bobcody.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
public class TodayIsHandler extends AbstractHandler {
    private static final DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DAY_MONTH_YEAR = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @Override
    protected String getResponseTextMessage(Message message) {

        StringBuilder currentDate = new StringBuilder();
        currentDate.append("Сегодня ")
                .append(LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL,
                        new Locale("ru", "RU")))
                .append(", ")
                .append(LocalDateTime.now().format(DAY_MONTH_YEAR
                        .withLocale(new Locale("ru", "RU"))))
                .append("\n")
                .append("время в Ижевске: ")
                .append(LocalDateTime.now(ZoneId.of("GMT+4")).format(HOUR_MINUTE))
                .append("\n")
                .append("в Дефолт-сити: ")
                .append(LocalDateTime.now(ZoneId.of("GMT+3")).format(HOUR_MINUTE))
                .append("\n");

        return currentDate.toString();
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getToday();
    }
}
