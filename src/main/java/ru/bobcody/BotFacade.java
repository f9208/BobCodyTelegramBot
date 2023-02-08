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
import ru.bobcody.updates.TelegramInputTextMessageResolver;


/**
 * фасад.
 * слой, который обрабатывает входящие update'ы.
 * <p>
 * фактически реализована обрабатка только Message (как поле update'a),
 * в которых содержится текст: Объект Message закидывается в handleInputTextMessage(Message message)
 * <p>
 * фотки и текстовые сообщения логируются, остальные тупо постятся в консоль
 */

@Setter
@Getter
@Component
public class BotFacade {
    //    @Autowired
//    private MainHandlerTextMessage mainHandlerTextMessage;
//
//    @Autowired
    private TelegramInputTextMessageResolver telegramInputTextMessageResolver;

    @Autowired
    private TextMessageService textMessageService;

    // BotApiMethods - A methods of Telegram Bots Api that is fully supported in json format
    public BotApiMethod<?> handleUserUpdate(Update update) {

        SendMessage replay = new SendMessage();

        Message message = getTelegramMessage(update);


        if (message.hasText()) {
            replay = textMessageService.replayInputMessage(message, update.hasEditedMessage());
//            replay = telegramInputTextMessageResolver.textMessageResolver(message);
//            outputTextMessageLog(replay, message);
        }

        if (message.hasAnimation()) {
//            writeLog(message);
        }
//
//        Boolean isUserChat = message.getChat().isUserChat();
//        if (Boolean.TRUE.equals(isUserChat)) {
//            if (message.hasPhoto()) {
//                writeLog(message);
//                replay = resolver.photoMessageResolver(message);
//            }
//            if (message.hasDocument()) {
//                writeLog(message);
//                if ("image/jpeg".equals(message.getDocument().getMimeType())) {
//                    replay = resolver.photoDocumentMessageResolver(message);
//                }
//                if ("video/mp4".equals(message.getDocument().getMimeType())) {
//                    replay = resolver.animationMessageResolver(message);
//                }
//            }
//        }
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