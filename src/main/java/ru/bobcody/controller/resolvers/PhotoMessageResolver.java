package ru.bobcody.controller.resolvers;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.controller.BobCodyBot;
import ru.bobcody.entities.Link;
import ru.bobcody.services.ChatService;
import ru.bobcody.services.GuestService;
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

@Getter
@Setter
@Component
public class PhotoMessageResolver {
    @Autowired
    BobCodyBot bobCodyBot;
    @Value("${imageSave.path}")
    private String rootPath;
    @Autowired
    LinkService linkService;
    @Autowired
    ChatService chatService;
    @Autowired
    GuestService guestService;
    //todo логгер не забыть
    @Value("${botloading.web-hook-path}")
    String rootUrl;
    String secondPartUrl = "/savedImages/";

    public SendMessage process(Message message) {
        SendMessage result = new SendMessage().setChatId(message.getChatId());
        PhotoSize photo = getBiggestPhoto(message);
        String telegramFilePath = getFilePath(photo);
        try {
            File file = translatePhotoAsFile(telegramFilePath);
            if (!checkFileIsImageType(file)) {
                return result.setText("твой файл не похож на картинку. не буду сохранять");
            }
            Path savedFilePath = saveFileOnDisk(file);
            Link savedLink = saveToDb(savedFilePath, file, message);
            String httpUrl = prepareHttpPath(savedLink.getName());
            return result.setText(httpUrl);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
        return result.setText("куда то там сохранили твою картинку. get off");
    }

    private PhotoSize getBiggestPhoto(final Message message) {
        List<PhotoSize> photos = message.getPhoto();
        PhotoSize biggestOne = photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
        return biggestOne;
    }

    private String getFilePath(final PhotoSize photo) {
        Objects.requireNonNull(photo);
        if (photo.hasFilePath()) {
            return photo.getFilePath();
        } else {
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                org.telegram.telegrambots.meta.api.objects.File telegramFile
                        = bobCodyBot.execute(getFileMethod);
                return telegramFile.getFilePath();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private File translatePhotoAsFile(final String filePath) throws TelegramApiException {
        File plainFile = bobCodyBot.downloadFile(filePath);
        return plainFile;
    }

    private Path saveFileOnDisk(final File inputFile) throws IOException {
        Path folder = Paths.get(rootPath);
        if (!Files.exists(folder)) {
            Files.createDirectory(folder);
        }

        String destFileName = FilenameUtils.getBaseName(String.valueOf(inputFile));
        Path destPath = Paths.get(folder.toString(), destFileName + ".jpg");

        Path result = copyFile(inputFile.toPath(), destPath);
        return result;
    }

    private Path copyFile(Path sourcePath, Path destPath) throws IOException {
        return Files.copy(sourcePath, destPath); //есть минимум 4 способа копировать файлы. этот самый короткий
    }

    private boolean checkFileIsImageType(final File file) {
        //todo как то надо проверять файл на консистентность чтобы не сохранять не жпг
        return true;
    }

    private Link saveToDb(Path path, File file, Message message) {
        Link forSave = new Link();
        forSave.setPath(path.toString());
        forSave.setDateCreated(LocalDateTime.now());
        forSave.setSize(file.getTotalSpace()); //todo тотал спейс это не размер файла
        forSave.setName(path.getFileName().toString());
        forSave.setGuest(guestService.findById(Long.valueOf(message.getFrom().getId())));
        forSave.setChat(chatService.getChatById(message.getChat().getId()));
        return linkService.saveLink(forSave);
    }

    private String prepareHttpPath(String name) {
        return rootUrl + secondPartUrl + name;
    }
}
