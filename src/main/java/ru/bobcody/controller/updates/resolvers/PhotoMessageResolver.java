package ru.bobcody.controller.updates.resolvers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;
import ru.bobcody.data.entities.Chat;
import ru.bobcody.data.entities.Guest;
import ru.bobcody.data.entities.Link;
import ru.bobcody.data.services.LinkService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static ru.bobcody.utilits.CommonTextConstant.*;

@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
public class PhotoMessageResolver implements IMessageResolver {
    private BobCodyBot bobCodyBot;
    @Value("${imageSave.path}")
    private String rootPath;
    private LinkService linkService;
    @Value("${botloading.web-hook-path}")
    private String rootUrl;
    private String prefixFolder = "/savedImages/";

    @Override
    public SendMessage process(Message message) {
        log.info("start extraction image for message {}", message.getMessageId());
        SendMessage result = new SendMessage(message.getChatId().toString(), SAVE_IMAGE_TRY);
        PhotoSize photo = getBiggestPhoto(message);
        Objects.requireNonNull(photo);
        String telegramFilePath;
        if (photo.getFilePath() != null) {
            telegramFilePath = photo.getFilePath();
        } else {
            telegramFilePath = getFilePath(photo.getFileId());
        }
        try {
            File file = translatePhotoAsFile(telegramFilePath);
            if (!checkFileIsImageType(file)) {
                log.error("file doesn't look like image-file. break");
                result.setText(NO_IMAGE);
                return result;
            }
            Path savedFilePath = saveFileOnDisk(file, ".jpg");
            saveLinkToDb(savedFilePath, file, message);
            String httpUrl = prepareHttpPath(savedFilePath.getFileName().toString());
            log.info("{}: result URL {}", message.getMessageId(), httpUrl);
            result.setText(httpUrl);
            return result;
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
            result.setText(SAVE_IMAGE_FAILURE);
        }
        return result;
    }

    /**
     * Если в объекте message и есть фотки то там их сразу list
     * с файлами разного размера. Для сохранения выбираем больший из них
     **/
    private PhotoSize getBiggestPhoto(final Message message) {
        log.info("looking for the biggest image");
        List<PhotoSize> photos = message.getPhoto();
        return photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
    }

    /**
     * Метод возвращает строку-Path по которой ее можно дернуть из телеграмма.
     * Это НЕ java.nio.file.Path;
     * File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.
     *
     * @param fileId - внутренний телеграмм-Id файла. длинная такая строка
     */
    public String getFilePath(String fileId) {
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(fileId);
        try {
            org.telegram.telegrambots.meta.api.objects.File telegramFile
                    = bobCodyBot.execute(getFileMethod);
            return telegramFile.getFilePath();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected java.io.File translatePhotoAsFile(final String filePath) throws TelegramApiException {
        return bobCodyBot.downloadFile(filePath);
    }

    protected Path saveFileOnDisk(final File inputFile, String extension) throws IOException {
        Path folder = Paths.get(rootPath);
        if (!Files.exists(folder)) {
            Files.createDirectory(folder);
        }
        String destFileName = FilenameUtils.getBaseName(String.valueOf(inputFile));
        Path destPath = Paths.get(folder.toString(), destFileName + extension);
        log.info("file has been saved: {} ", destPath.toString());
        return copyFile(inputFile.toPath(), destPath);
    }

    private Path copyFile(Path sourcePath, Path destPath) throws IOException {
        log.info("copy temporary file {} on disk {}", sourcePath, destPath);
        return Files.copy(sourcePath, destPath);
    }

    protected boolean checkFileIsImageType(final File file) {
        boolean valid = true;
        try {
            Image image = ImageIO.read(file);
            if (image == null) {
                valid = false;
                log.error("The file {} could not be opened, it is not an image. Break", file.getName());
            }
        } catch (IOException ex) {
            valid = false;
            log.error("The file {} could not be opened, an error occurred. Break", file.getName());
        }
        return valid;
    }

    protected void saveLinkToDb(Path path, File file, Message message) {
        Link forSave = new Link(path.toString(), path.getFileName().toString());
        forSave.setDateCreated(LocalDateTime.now());
        forSave.setSize(file.length());
        forSave.setGuest(new Guest(message.getFrom()));
        forSave.setChat(new Chat(message.getChat()));
        linkService.saveLink(forSave);
    }

    protected String prepareHttpPath(String name) {
        return rootUrl + prefixFolder + name;
    }
}
