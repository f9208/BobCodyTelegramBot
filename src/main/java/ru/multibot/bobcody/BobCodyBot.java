package ru.multibot.bobcody;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.multibot.bobcody.BotFacade;


@Setter
@Getter
@ConfigurationProperties (prefix = "botloading")
public class BobCodyBot extends TelegramWebhookBot {
    String botName;
    String botToken;
    String webHookPath;
    @Autowired
    BotFacade botFacade;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        BotApiMethod result=botFacade.handleUserUpdate(update);
//        все что закомментено выкидывает лося. как то это обернуть.
//        try {
//            this.execute(new SendAnimation().setAnimation("CgACAgIAAxkBAAIOU19X2Fq4QYnUI15KI4h8MAYj4_WGAAJjCQACE5zBShCPD2aK8whrGwQ").setChatId(update.getMessage().getChatId()));
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
        return result;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
