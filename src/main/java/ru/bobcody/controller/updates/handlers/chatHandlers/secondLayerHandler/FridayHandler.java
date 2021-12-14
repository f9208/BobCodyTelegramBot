package ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chatHandlers.IHandler;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.utils.ElkExecutor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
@PropertySource(value = "classpath:additional.properties", encoding = "UTF-8")
public class FridayHandler implements IHandler {
    @Value("${friday.command}")
    private List<String> commands;
    @Autowired
    ElkExecutor elkExecutor;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getText().split(" ").length == 1) {
            if (todayIs() == DayOfWeek.FRIDAY) {
                elkExecutor.executeFriday(inputMessage.getChatId().toString());
            } else {
                result.setText(notFridayAnswer());
            }
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private String notFridayAnswer() {
        String result = "";
        if (todayIs() == DayOfWeek.MONDAY ||
                todayIs() == DayOfWeek.TUESDAY ||
                todayIs() == DayOfWeek.WEDNESDAY)
            result = "сегодня "
                    + todayIs().getDisplayName(TextStyle.FULL,
                    new Locale("ru", "RU")) + ". работай давай!";

        if (todayIs() == DayOfWeek.THURSDAY)
            result = "Сегодня - четверг. А четверг - маленькая пятница! а большая - завтра.";

        if (todayIs() == DayOfWeek.SUNDAY ||
                LocalDateTime.now().getDayOfWeek() == DayOfWeek.SATURDAY)
            result = "че за вопросы? сегодня же " +
                    todayIs().getDisplayName(TextStyle.FULL,
                            new Locale("ru", "RU")) + "! гуляй, рванина!";
        return result;
    }

    private DayOfWeek todayIs() {
        return LocalDateTime.now().getDayOfWeek();
    }
}