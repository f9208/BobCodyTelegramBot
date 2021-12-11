package ru.bobcody;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Setter
@Getter
@ConfigurationProperties(prefix = "botloading")
public class BobCodyBot extends TelegramWebhookBot {
    String botName;
    String botToken;
    String webHookPath;

    @Autowired
    @Lazy
    BotFacade botFacade;

    public BobCodyBot() {
    }

    public BobCodyBot(String botName, String botToken, String webHookPath) {
        this.botName = botName;
        this.botToken = botToken;
        this.webHookPath = webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return botFacade.handleUserUpdate(update);
        } catch (Exception e) {
            e.printStackTrace();
            return new SendMessage(update.getMessage().getChatId().toString(), "что то пошло не так. я сломался");
        }
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
