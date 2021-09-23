package ru.bobcody.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;

import static ru.bobcody.utilits.MessageWriteLog.*;


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
@Setter
@Getter
@Component
public class BotFacade {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    FloodControll floodControll;
    @Autowired
    Resolver resolver;

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
        return replay;
    }

    private static boolean hasMessage(Update update) {
        return update.hasMessage();
    }

    private static boolean hasEditedMessage(Update update) {
        return update.hasEditedMessage();
    }
}