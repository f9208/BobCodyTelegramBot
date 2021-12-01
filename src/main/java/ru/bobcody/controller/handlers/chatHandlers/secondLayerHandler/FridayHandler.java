package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
@PropertySource(value = "classpath:additional.properties", encoding = "UTF-8")
public class FridayHandler implements SimpleHandlerInterface {
    @Autowired
    @Lazy
    private BobCodyBot bobCodyBot;
    @Value("${friday.command}")
    private List<String> commands;
    @Value("${elk.path}")
    private String elkPath;
    @Value("${izhmain.chat.id}")
    private String mainChatId;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getText().split(" ").length == 1) {
            if (LocalDateTime.now().getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridayAnswerGif(inputMessage);
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
        if (LocalDateTime.now().getDayOfWeek() == DayOfWeek.MONDAY ||
                LocalDateTime.now().getDayOfWeek() == DayOfWeek.TUESDAY ||
                LocalDateTime.now().getDayOfWeek() == DayOfWeek.WEDNESDAY)
            result = "сегодня "
                    + LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL,
                    new Locale("ru", "RU")) + ". работай давай!";

        if (LocalDateTime.now().getDayOfWeek() == DayOfWeek.THURSDAY)
            result = "Сегодня - четверг. А четверг - маленькая пятница! а большая - завтра.";

        if (LocalDateTime.now().getDayOfWeek() == DayOfWeek.SUNDAY ||
                LocalDateTime.now().getDayOfWeek() == DayOfWeek.SATURDAY)
            result = "че за вопросы? сегодня же " +
                    LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL,
                            new Locale("ru", "RU")) + "! гуляй, рванина!";
        return result;
    }

    private void fridayAnswerGif(Message message) {
        try {
            log.info("try to send elk-gif");
            InputFile inputFile = new InputFile(elkPath);
            SendAnimation elkFriday = new SendAnimation();
            elkFriday.setAnimation(inputFile);
            elkFriday.setChatId(message.getChatId().toString());
            bobCodyBot.execute(elkFriday);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("send elk-gif failure");
        }
    }

    @Scheduled(cron = "0 00 8 * * FRI ")
    private void sendFridayGif() {
        try {
            log.info("try to send elk-gif");
            InputFile inputFile = new InputFile(elkPath);
            SendAnimation elkFriday = new SendAnimation();
            elkFriday.setAnimation(inputFile);
            elkFriday.setChatId(mainChatId);
            bobCodyBot.execute(elkFriday);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("send elk-gif failure");
        }
    }
}
