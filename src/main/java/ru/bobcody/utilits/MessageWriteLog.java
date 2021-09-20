package ru.bobcody.utilits;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class MessageWriteLog {

    private static final String LOG_COMMON_MESSAGE = "chatID: {}, time: {}, userName: {}, userId: {}, ";
    private static final String UNIQUE_ID_FILE_NAME = "uniqueId: {}, fileName: {}";

    public static void writeLog(Message message) {
        if (message.hasPhoto()) {
            inputMessagePhotoLog(message);
        }
        if (message.hasText()) {
            inputMessageTextLog(message);
        }
        if (message.hasAnimation()) {
            inputMessageAnimationLog(message);
        }

        if (message.hasDocument()) {
            inputMessageDocumentLog(message);
        }

        if (message.hasAudio()) {
            inputMessageAudioLog(message);
        }
    }

    private static void inputMessageTextLog(Message message) {
        log.info("Input text message, " + LOG_COMMON_MESSAGE + "textMessage: {}, " + "messageId: {}",
                compileArgs(commonArguments(message), message.getText(), message.getMessageId()));
    }

    private static void inputMessagePhotoLog(Message message) {
        List<PhotoSize> listInputPhoto = message.getPhoto();
        for (PhotoSize oneSinglePhoto : listInputPhoto) {
            log.info("Input Photo, " + LOG_COMMON_MESSAGE + "photoSize: {}",
                    compileArgs(commonArguments(message), oneSinglePhoto));
        }
    }

    private static void inputMessageAnimationLog(Message message) {
        log.info("Input animation, " + LOG_COMMON_MESSAGE + UNIQUE_ID_FILE_NAME,
                compileArgs(commonArguments(message), animationArguments(message)));
    }

    private static void inputMessageDocumentLog(Message message) {
        log.info("Input document, " + LOG_COMMON_MESSAGE + UNIQUE_ID_FILE_NAME,
                compileArgs(commonArguments(message), documentArguments(message)));
    }

    private static void inputMessageAudioLog(Message message) {
        log.info("Input audio, " + LOG_COMMON_MESSAGE + "audio: {}",
                compileArgs(commonArguments(message), audioArguments(message)));
    }

    public static void outputTextMessageLog(SendMessage sendMessage, Message message) {
        log.info("Output text message, chatID: {}, time: {}, userName: BobCody, textMessage: {}, " + "messageId: {}",
                sendMessage.getChatId(),
                LocalTime.now(),
                sendMessage.getText(),
                message.getMessageId() + 1);
    }

    private static Object[] commonArguments(Message message) {
        return new Object[]{message.getChatId(),
                new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(message.getDate().longValue() * 1000)),
                message.getFrom().getUserName(),
                message.getFrom().getId()
        };
    }

    private static Object[] documentArguments(Message message) {
        return new Object[]{message.getDocument().getFileUniqueId(),
                message.getDocument().getFileName()
        };
    }

    private static Object[] animationArguments(Message message) {
        return new Object[]{message.getAnimation().getFileUniqueId(), message.getAnimation().getFileName()};
    }

    private static Object[] audioArguments(Message message) {
        return new Object[]{message.getAudio()};
    }

    private static Object[] compileArgs(Object[] inputArrays, Object... additionFields) {
        List results = new ArrayList(Arrays.asList(inputArrays));
        results.addAll(Arrays.asList(additionFields));
        return results.toArray();
    }
}