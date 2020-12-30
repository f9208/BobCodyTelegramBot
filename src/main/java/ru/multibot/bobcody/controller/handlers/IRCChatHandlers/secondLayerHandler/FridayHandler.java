package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.multibot.bobcody.controller.BobCodyBot;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@Getter
@Setter
public class FridayHandler implements SimpleHandlerInterface {
    @Autowired
    BobCodyBot bobCodyBot;

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
            bobCodyBot.execute(new SendAnimation().setAnimation("CgACAgIAAxkBAAPyX6rFVF8sQ4KQQHJ_h0Ue-91x5L0AAmMJAAITnMFK0pd6SVksFeweBA")
                    .setChatId(message.getChatId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 00 8 * * FRI ")
    private void sendFridayGif() {
        try {
            bobCodyBot.execute(new SendAnimation()
                    .setAnimation("CgACAgIAAxkBAAPyX6rFVF8sQ4KQQHJ_h0Ue-91x5L0AAmMJAAITnMFK0pd6SVksFeweBA")
                    .setChatId("-1001207502467"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

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
        List<String> commands = new ArrayList<>();
        commands.add("пятница");
        commands.add("!пятница");
        commands.add("!friday");
        commands.add("friday");
        commands.add("!дн");
        commands.add("!dow");
        return commands;
    }
}
