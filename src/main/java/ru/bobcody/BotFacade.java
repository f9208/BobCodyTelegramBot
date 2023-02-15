package ru.bobcody;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.services.TextMessageService;

@Setter
@Getter
@Component
public class BotFacade {
    @Autowired
    private TextMessageService textMessageService;

    public BotApiMethod<?> handleUpdate(Update update) {

        SendMessage replay = new SendMessage();

        Message message = getTelegramMessage(update);

        if (message.hasText()) {
            replay = textMessageService.replyInputMessage(message, update.hasEditedMessage());
        }

        return replay;
    }

    private Message getTelegramMessage(Update update) {
        Message message = new Message();

        if (update.hasEditedMessage()) {
            message = update.getEditedMessage();
        }

        if (update.hasMessage()) {
            message = update.getMessage();
        }

        return message;
    }
}