package ru.bobcody.controller.resolvers;

import lombok.Getter;
import lombok.Setter;
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
import ru.bobcody.entities.Chat;
import ru.bobcody.entities.Guest;
import ru.bobcody.entities.Link;
import ru.bobcody.services.LinkService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@Component
public class PhotoMessageResolver extends AbstractMessageResolver {
    @Autowired
    BobCodyBot bobCodyBot;
    @Value("${imageSave.path}")
    private String rootPath;
    @Autowired
    LinkService linkService;
    @Value("${botloading.web-hook-path}")
    String rootUrl;
    String prefixFolder = "/savedImages/";

    @Override
    public SendMessage process(Message message) {
        log.info("start extraction image for message {}", message.getMessageId());
        SendMessage result = new SendMessage(message.getChatId().toString(), "я попробовал сохранить твою картинку");
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
                result.setText("твой файл не похож на картинку. не буду сохранять");
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
            result.setText("не удалось сохранить изображение");
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
        return Files.copy(sourcePath, destPath); //todo есть минимум 4 способа копировать файлы. этот самый короткий
    }

    protected boolean checkFileIsImageType(final File file) {
        //todo как то надо проверять файл на консистентность чтобы не сохранять не жпг
        return true;
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
