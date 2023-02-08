package ru.bobcody;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.services.SettingService;

@Setter
@Getter
@NoArgsConstructor
@Component
public class BobCodyBot extends TelegramWebhookBot {
    @Autowired
    private SettingService settingService;

    @Autowired
    private BotFacade botFacade;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
//        try {
//            System.out.println(update);
//            if (update.getMessage() != null) {
//                System.out.println(update.getMessage().getText());
//            }
//            botFacade.handleUpdate(update);
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new SendMessage(update.getMessage().getChatId().toString(), SMTH_GET_WRONG_BROKEN);
//        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return settingService.getBotName();
    }

    @Override
    public String getBotPath() {
        return settingService.getWebHookPath();
    }


    public String getBotToken() {
        return settingService.getBotToken();
    }
}
