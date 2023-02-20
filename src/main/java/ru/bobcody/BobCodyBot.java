package ru.bobcody;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.services.SettingService;

@Setter
@Getter
@Component
public class BobCodyBot extends TelegramWebhookBot {
    private SettingService settingService;

    public BobCodyBot(SettingService settingService) {
        super(settingService.getBotToken());
        this.settingService = settingService;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return new SendMessage();
    }

    @Override
    public String getBotUsername() {
        return settingService.getBotName();
    }

    @Override
    public String getBotPath() {
        return settingService.getWebHookPath();
    }
}
