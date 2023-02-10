package ru.bobcody.updates.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.SendElkCommand;
import ru.bobcody.services.SettingService;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.TextConstantHandler;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
public class FridayHandler extends AbstractHandler {

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getText().split(" ").length == 1) {
            if (todayIs() == DayOfWeek.FRIDAY) {

                String chatId = inputMessage.getChatId().toString();

                executeCommand(new SendElkCommand(chatId));

                result.setText(getResponseTextMessage(inputMessage));     // телеграм не пересылает пустые SendMessage

            } else {
                result.setText(notFridayAnswer());
            }
        }
        return result;
    }

    @Override
    protected String getResponseTextMessage(Message message) {
        return "";
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getFriday();
    }

    private String notFridayAnswer() {
        String result = "";
        DayOfWeek dow = todayIs();
        if (dow == DayOfWeek.MONDAY ||
                dow == DayOfWeek.TUESDAY ||
                dow == DayOfWeek.WEDNESDAY)
            result = String.format(TextConstantHandler.MO_TU_WE_TODAY, dow.getDisplayName(TextStyle.FULL,
                    new Locale("ru", "RU")));
        if (dow == DayOfWeek.THURSDAY)
            result = TextConstantHandler.THURSDAY_TODAY;
        if (dow == DayOfWeek.SUNDAY ||
                LocalDateTime.now().getDayOfWeek() == DayOfWeek.SATURDAY)
            result = String.format(TextConstantHandler.SUNDAY_TODAY, dow.getDisplayName(TextStyle.FULL,
                    new Locale("ru", "RU")));
        return result;
    }

    private DayOfWeek todayIs() {
        return LocalDateTime.now().getDayOfWeek();
    }
}