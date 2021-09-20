package ru.bobcody.controller;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;


@Setter
@Getter
@ConfigurationProperties(prefix = "botloading")
public class BobCodyBot extends TelegramWebhookBot {
    String botName;
    String botToken;
    String webHookPath;

    @Autowired
    BotFacade botFacade;

    public BobCodyBot() {
    }

    public BobCodyBot(String botName, String botToken, String webHookPath) {
        this.botName=botName;
        this.botToken=botToken;
        this.webHookPath=webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        BotApiMethod result = botFacade.handleUserUpdate(update);
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
