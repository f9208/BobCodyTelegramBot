package ru.multibot.bobcody.controller;


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

    /**
     * Основной метод, в который прилетают updatы из Request-POST части.
     * Возвращать, соответственно, можно только наследников BotApiMethod'a (SendMessage, например)
     * Чтобы бот отправлял файл, картинку, музыку и прочие не JSON объекты
     * необходимо executнуть инстенс bobCodyBot с этим файлом (картинкой, музыкой, etс)
     * в качестве параметра
     */
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
