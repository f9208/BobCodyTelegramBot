package ru.bobcody;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.controller.Resolver;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;

import static ru.bobcody.utilits.MessageWriteLog.outputTextMessageLog;
import static ru.bobcody.utilits.MessageWriteLog.writeLog;


/**
 * фасад.
 * слой, который обрабатывает входящие update'ы.
 * <p>
 * фактически реализована обрабатка только Message (как поле update'a),
 * в которых содержится текст: Объект Message закидывается в handleInputTextMessage(Message message)
 * <p>
 * фотки и текстовые сообщения логируются, остальные тупо постятся в консоль
 */
@Slf4j
@Component
public class BotFacade {
    @Autowired
    private MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    private Resolver resolver;

    // BotApiMethods - A methods of Telegram Bots Api that is fully supported in json format
    public BotApiMethod<?> handleUserUpdate(Update update) {

        SendMessage replay = null;
        Message message = null;
        if (hasEditedMessage(update)) {
            message = update.getEditedMessage();
            writeLog(message);
        }
        if (hasMessage(update)) {
            message = update.getMessage();
            writeLog(message);
        }
        if (message != null && message.hasText()) {
            replay = resolver.textMessageResolver(message, hasEditedMessage(update));
            if (replay.getText() != null) {
                outputTextMessageLog(replay, message);
            }
        }
        if (message != null && message.hasAnimation()) {
            writeLog(message);
        }
        if (message != null && message.hasPhoto()) {
            writeLog(message);
//            replay = resolver.photoMessageResolver(message);
        }
        if (message != null && message.hasDocument()) {
            writeLog(message);
//            if ("image/jpeg".equals(message.getDocument().getMimeType())) {
//                replay = resolver.photoDocumentMessageResolver(message);
//            }
//            if ("video/mp4".equals(message.getDocument().getMimeType())) {
//                replay = resolver.animationMessageResolver(message);
//            }
        }
        return replay;
    }

    private static boolean hasMessage(Update update) {
        return update.hasMessage();
    }

    private static boolean hasEditedMessage(Update update) {
        return update.hasEditedMessage();
    }
}