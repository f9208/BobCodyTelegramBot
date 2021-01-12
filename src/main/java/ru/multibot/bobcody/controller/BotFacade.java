package ru.multibot.bobcody.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.IRCMainHandlerTextMessage;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        Message inputMessage = update.getMessage();

        if (inputMessage != null && inputMessage.hasAnimation()) {
            System.out.println("анимация");
        }

        if (inputMessage != null && inputMessage.hasPhoto()) {
            System.out.println("фото");
        }
        if (inputMessage != null && inputMessage.hasDocument()) {
            System.out.println(inputMessage.getDocument().getFileId());
        }

        if (inputMessage != null && inputMessage.hasAudio()) {
            System.out.println(inputMessage.getAudio().getFileUniqueId());
            System.out.println(inputMessage.getAudio().getFileId());
        }

        // логирование фоток
        if (inputMessage != null && inputMessage.hasPhoto()) {
            List<PhotoSize> listInputPhoto = inputMessage.getPhoto();
            for (PhotoSize oneSinglePhoto : listInputPhoto) {
                log.info("Input Photo " +
                                " chatID: {}" +
                                " time: {}" +
                                " userName: {}," +
                                " photo: {}", inputMessage.getChatId(),
                        new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(inputMessage.getDate().longValue() * 1000)),
                        inputMessage.getFrom().getUserName(),
                        oneSinglePhoto
                );
            }
        }
        // логгирование сообщений, содержащих текст
        if (inputMessage != null && inputMessage.hasText()) {
            log.info("Input, " +
                            " chatID: {}," +
                            " time: {}," +
                            " userName: {}," +
                            " userId: {}," +
                            " textMessage: \"{}\"",
                    inputMessage.getChatId(),
                    new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(inputMessage.getDate().longValue() * 1000)),
                    inputMessage.getFrom().getUserName(),
                    inputMessage.getFrom().getId(),
                    inputMessage.getText());

            replay = handleInputTextMessage(inputMessage);
            if (((SendMessage) replay).getText() != null) log.info("Output, " +
                            " chatID: {}," +
                            " time: {}," +
                            " userName: BobCody," +
                            " textMessage: \"{}\"",
                    ((SendMessage) replay).getChatId(),
                    LocalTime.now(),
                    ((SendMessage) replay).getText());

        } else if (update.hasCallbackQuery()) {
            log.info("new CallBack: {}",
                    update.getCallbackQuery().getData());
            replay = new SendMessage().setChatId(update.getMessage().getChatId()).setText("в колбэках пока не знаю");
        }
        return replay;
    }

    private SendMessage handleInputTextMessage(Message message) {
        SendMessage replay = new SendMessage();
        try {
                replay = ircMainHandlerTextMessage.handle(message);
            if (replay.getText() != null) floodControll.filter(message);
        } catch (Exception e) {
            e.printStackTrace();
            replay.setText("что-то пошло не так ").setChatId("445682905");
        }
        return replay;
    }
}

