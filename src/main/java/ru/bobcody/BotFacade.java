package ru.bobcody;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.controller.updates.Resolver;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;

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

@Setter
@Getter
@Component
@RequiredArgsConstructor
public class BotFacade {
    private final MainHandlerTextMessage mainHandlerTextMessage;
    private final Resolver resolver;

    // BotApiMethods - A methods of Telegram Bots Api that is fully supported in json format
    public BotApiMethod<?> handleUserUpdate(Update update) {

        SendMessage replay = new SendMessage();
        Message message = new Message();
        if (hasEditedMessage(update)) {
            message = update.getEditedMessage();
            writeLog(message);
        }
        if (hasMessage(update)) {
            message = update.getMessage();
            writeLog(message);
        }
        if (message.hasText()) {
            replay = resolver.textMessageResolver(message, hasEditedMessage(update));
            outputTextMessageLog(replay, message);
        }
        if (message.hasAnimation()) {
            writeLog(message);
        }

        Boolean isUserChat = message.getChat().isUserChat();
        if (Boolean.TRUE.equals(isUserChat)) {
            if (message.hasPhoto()) {
                writeLog(message);
                replay = resolver.photoMessageResolver(message);
            }
            if (message.hasDocument()) {
                writeLog(message);
                if ("image/jpeg".equals(message.getDocument().getMimeType())) {
                    replay = resolver.photoDocumentMessageResolver(message);
                }
                if ("video/mp4".equals(message.getDocument().getMimeType())) {
                    replay = resolver.animationMessageResolver(message);
                }
            }
        }
        return replay;
    }

    private boolean hasMessage(Update update) {
        return update.hasMessage();
    }

    private boolean hasEditedMessage(Update update) {
        return update.hasEditedMessage();
    }
}