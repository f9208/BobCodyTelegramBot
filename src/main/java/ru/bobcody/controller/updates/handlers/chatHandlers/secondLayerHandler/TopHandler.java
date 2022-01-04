package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.IHandler;
import ru.bobcody.data.services.TextMessageService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class TopHandler implements IHandler {
    @Value("${top.command}")
    private List<String> commands;
    @Autowired
    TextMessageService textMessageService;
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
        String currentCommand = findCommand(inputMessage.getText());
        if ("FULL".equals(currentCommand)) {
            result.setText(wrapper.getFullTop(chatId));
        }
        if ("MONTH".equals(currentCommand)) {
            result.setText(wrapper.getForMonth(chatId));
        }
        if ("TODAY".equals(currentCommand)) {
            result.setText(wrapper.getForToday(chatId));
        }
        return result;
    }

    private String findCommand(String inputStr) {
        switch (inputStr.trim()) {
            case ("!top month"):
            case ("!топ месяц"):
                return "MONTH";
            case ("!топ сегодня"):
            case ("!top today"):
                return "TODAY";
            case ("!top"):
            case ("!топ"):
            default:    return "FULL";
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
}


