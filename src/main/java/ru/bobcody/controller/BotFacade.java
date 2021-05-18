package ru.bobcody.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.controller.handlers.IRCChatHandlers.IRCMainHandlerTextMessage;

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
    IRCMainHandlerTextMessage ircMainHandlerTextMessage;
    @Autowired
    FloodControll floodControll;


    public BotApiMethod<?> handleUserUpdate(Update update) {

        BotApiMethod replay = null;

        if (hasMessage(update)) {
            Message inputMessage = update.getMessage();
            writeLog(inputMessage);

            if (inputMessage.hasText()) {
                replay = messageResolver(inputMessage);
                if (((SendMessage) replay).getText() != null) {
                    outputTestMessageLog((SendMessage) replay);
                }
            }
            if (hasCallback(update)) {
                return callBackResolver(update);
            }
        }
        return replay;
    }

    private SendMessage messageResolver(Message message) {
        SendMessage replay = new SendMessage();
        try {
            replay = ircMainHandlerTextMessage.handle(message);
        } catch (Exception e) {
            e.printStackTrace();
            replay.setText("что-то пошло не так: " + e.toString()).setChatId("445682905");
        }
        return replay;
    }

    private SendMessage callBackResolver(Update update) {
        log.info("new CallBack: {}", update.getCallbackQuery().getData());
        return new SendMessage();
    }

    private static boolean hasMessage(Update update) {
        return update.hasMessage();
    }

    private static boolean hasCallback(Update update) {
        return update.hasCallbackQuery();
    }
}