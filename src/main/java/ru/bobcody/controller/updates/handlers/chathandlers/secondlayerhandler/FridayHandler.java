package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.IHandler;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.ElkExecutor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.*;

@Slf4j
@Component
@PropertySource(value = "classpath:additional.properties", encoding = "UTF-8")
@RequiredArgsConstructor
public class FridayHandler implements IHandler {
    @Value("${friday.command}")
    private List<String> commands;
    private final ElkExecutor elkExecutor;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getText().split(" ").length == 1) {
            if (todayIs() == DayOfWeek.FRIDAY) {
                elkExecutor.executeFriday(inputMessage.getChatId().toString());
                result.setText("");     // телеграмм не пересылает пустые SendMessage. вписываю "" чтобы в тестах не было NPE
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
        DayOfWeek dow = todayIs();
        if (dow == DayOfWeek.MONDAY ||
                dow == DayOfWeek.TUESDAY ||
                dow == DayOfWeek.WEDNESDAY)
            result = String.format(MO_TU_WE_TODAY, dow.getDisplayName(TextStyle.FULL,
                    new Locale("ru", "RU")));
        if (dow == DayOfWeek.THURSDAY)
            result = THURSDAY_TODAY;
        if (dow == DayOfWeek.SUNDAY ||
                LocalDateTime.now().getDayOfWeek() == DayOfWeek.SATURDAY)
            result = String.format(SUNDAY_TODAY, dow.getDisplayName(TextStyle.FULL,
                    new Locale("ru", "RU")));
        return result;
    }

    private DayOfWeek todayIs() {
        return LocalDateTime.now().getDayOfWeek();
    }
}