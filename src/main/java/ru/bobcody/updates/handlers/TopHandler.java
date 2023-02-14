package ru.bobcody.updates.handlers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.GetReportOverChatCommand;
import ru.bobcody.updates.handlers.AbstractHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TopHandler extends AbstractHandler {
    private static final Map<String, ConsiderationPeriod> CONSIDERATION_PERIOD_MAP = new LinkedHashMap<>();

    static {
        CONSIDERATION_PERIOD_MAP.put("", new AllTimePeriod());
        CONSIDERATION_PERIOD_MAP.put("день", new TodayPeriod());
        CONSIDERATION_PERIOD_MAP.put("сегодня", new TodayPeriod());
        CONSIDERATION_PERIOD_MAP.put("day", new TodayPeriod());
        CONSIDERATION_PERIOD_MAP.put("месяц", new CurrentMonthPeriod());
        CONSIDERATION_PERIOD_MAP.put("month", new CurrentMonthPeriod());
    }

    @Override
    protected String getResponseTextMessage(Message message) {
        String param = getParameterOverDirective(message.getText(), 1);

        Long chatId = message.getChatId();

        ConsiderationPeriod considerationPeriod;
        LocalDateTime after;

        if (CONSIDERATION_PERIOD_MAP.containsKey(param)) {
            considerationPeriod = CONSIDERATION_PERIOD_MAP.get(param);
            after = considerationPeriod.getPeriodAfter();

        } else {
            return String.format("параметр '%s' не найден. \n" +
                            "Доступные параметры: %s",
                    param,
                    getAvailableParameters());
        }

        LocalDateTime before = considerationPeriod.getPeriodBefore();

        List<String> statistic = executeCommand(new GetReportOverChatCommand(chatId, after, before));

        return considerationPeriod.prefixResponseMessage() + String.join("\n", statistic);
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getTop();
    }

    private String getAvailableParameters() {
        return CONSIDERATION_PERIOD_MAP.keySet()
                .stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(", "));
    }

    private interface ConsiderationPeriod {
        default LocalDateTime getPeriodBefore() {
            return LocalDateTime.now();
        }

        LocalDateTime getPeriodAfter();

        String prefixResponseMessage();
    }

    private static class TodayPeriod implements ConsiderationPeriod {

        @Override
        public LocalDateTime getPeriodAfter() {
            return LocalDate.now().atStartOfDay();
        }

        @Override
        public String prefixResponseMessage() {
            return "Топ болтунов за сегодня: \n";
        }
    }

    private static class CurrentMonthPeriod implements ConsiderationPeriod {

        @Override
        public LocalDateTime getPeriodAfter() {
            LocalDate firstDayOfMonth = LocalDate.of(LocalDate.now().getYear(),
                    LocalDate.now().getMonth(),
                    1);

            return LocalDateTime.of(firstDayOfMonth, LocalTime.MIDNIGHT);
        }

        @Override
        public String prefixResponseMessage() {
            return "Топ болтунов за месяц: \n";
        }
    }

    private static class AllTimePeriod implements ConsiderationPeriod {

        @Override
        public LocalDateTime getPeriodAfter() {
            return LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
        }

        @Override
        public String prefixResponseMessage() {
            return "Топ болтунов за все время: \n";
        }
    }
}
