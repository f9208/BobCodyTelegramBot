package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@Getter
@Setter
public class TodayIsHandler implements SimpleHandlerInterface {
    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();

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
                .append(LocalDateTime.now(ZoneId.of("GMT+1")).format(DateTimeFormatter.ofPattern("HH:mm")))
                .append("\n")
                .append("в солнечной Калифорнии: ")
                .append(LocalDateTime.now(ZoneId.of("GMT-8")).format(DateTimeFormatter.ofPattern("HH:mm")))
                .append(" (")
                .append(LocalDateTime.now(ZoneId.of("GMT-8")).format(DateTimeFormatter.ofPattern("dd MMMM")
                        .withLocale(new Locale("ru", "RU"))))
                .append(")\n");
        result.setText(currentDate.toString());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!сегодня");
        commands.add("!дата");
        commands.add("!today");
        commands.add("!время");
        commands.add("!time");
        commands.add("!ща");
        commands.add("!now");
        commands.add("!time");
        return commands;
    }
}
