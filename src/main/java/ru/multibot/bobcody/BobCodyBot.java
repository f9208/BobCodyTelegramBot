package ru.multibot.bobcody;


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


    /**
        Основной метод, в который прилетают update.
        Возвращает, соответственно, только наследников BotApiMethod'a.
        Чтобы отправить бот отправлял файл, картинку, музыку и прочие не JSON объекты
        необходимо execute инстенс bobCodyBot с этим файлом (картинкой, музыкой, etс)
        в качестве параметра
    */
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        System.out.println("чивота было");
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
