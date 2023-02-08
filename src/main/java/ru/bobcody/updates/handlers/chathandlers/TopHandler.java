package ru.bobcody.updates.handlers.chathandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.services.TextMessageService;
import ru.bobcody.updates.handlers.IHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.bobcody.updates.handlers.chathandlers.TopHandler.CommandsType.*;

//@Component
@RequiredArgsConstructor
public class TopHandler implements IHandler {
    @Value("${top.command}")
    private List<String> commands;
    private final TextMessageService textMessageService;
    @Value("${top.all}")
    private String messagePrefixAll;
    @Value("${top.month}")
    private String messagePrefixMonth;
    @Value("${top.today}")
    private String messagePrefixToday;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        Wrapper wrapper = new Wrapper();
        long chatId = inputMessage.getChatId();
        CommandsType current = findCommand(inputMessage.getText());
        switch (current) {
            case FULL:
                result.setText(wrapper.getFullTop(chatId));
                break;
            case MONTH:
                result.setText(wrapper.getForMonth(chatId));
                break;
            case TODAY:
                result.setText(wrapper.getForToday(chatId));
                break;
            default:
                break;
        }
        return result;
    }

    private CommandsType findCommand(String inputStr) {
        switch (inputStr.trim()) {
            case ("!top month"):
            case ("!топ месяц"):
                return MONTH;
            case ("!топ сегодня"):
            case ("!top today"):
                return TODAY;
            case ("!top"):
            case ("!топ"):
            default:
                return FULL;
        }
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private class Wrapper {
        final LocalDateTime now = LocalDateTime.now();

        String getFullTop(long chatId) {
            LocalDateTime start = LocalDateTime.parse("2000-01-01T00:00:00.000");
            return messagePrefixAll + wrapTop(textMessageService.getTop(chatId, start, now));
        }

        String getForToday(long chatId) {
            LocalDateTime start = LocalDate.now().atStartOfDay();
            return messagePrefixToday + wrapTop(textMessageService.getTop(chatId, start, now));
        }

        String getForMonth(long chatId) {
            LocalDate dateToday = LocalDate.now();
            LocalDate monthStart = LocalDate.of(dateToday.getYear(), dateToday.getMonth(), 1);
            LocalDateTime start = LocalDateTime.of(monthStart, LocalTime.MIN);
            return messagePrefixMonth + wrapTop(textMessageService.getTop(chatId, start, now));
        }

        String wrapTop(List<String> lines) {
            StringBuilder result = new StringBuilder();
            int index = 1;
            for (String note : lines) {
                result.append(index++)
                        .append(". ")
                        .append(note.split(",")[0])
                        .append(" - ")
                        .append(note.split(",")[1])
                        .append("\n");
            }
            return result.toString();
        }
    }

    enum CommandsType {
        TODAY, MONTH, FULL;
    }
}
