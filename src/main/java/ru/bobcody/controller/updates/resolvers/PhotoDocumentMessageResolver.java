package ru.bobcody.controller.updates.resolvers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static ru.bobcody.utilits.CommonTextConstant.*;

@Slf4j
@Component
public class PhotoDocumentMessageResolver extends PhotoMessageResolver {
    @Override
    public SendMessage process(Message message) {
        log.info("start processing a document with image {}", message.getDocument().getFileName());
        SendMessage result = new SendMessage(message.getChatId().toString(), SAVE_FILE_TRY);
        Document document = message.getDocument();
        Objects.requireNonNull(document);
        String telegramFilePath = getFilePath(document.getFileId());
        try {
            File file = translatePhotoAsFile(telegramFilePath);
            if (!checkFileIsImageType(file)) {
                result.setText(NO_IMAGE);
                return result;
            }
            Path savedFilePath = saveFileOnDisk(file, ".jpg");
            saveLinkToDb(savedFilePath, file, message);
            String httpUrl = prepareHttpPath(savedFilePath.getFileName().toString());
            log.info("messageId{}: result URL {}", message.getMessageId(), httpUrl);
            result.setText(httpUrl);
            return result;
        } catch (TelegramApiException | IOException e) {
            log.error("save file failure. Cause: ", e.getCause());
            e.printStackTrace();
            result.setText(SAVE_FILE_FAILURE);
        }
        return result;
    }
}
