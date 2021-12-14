package ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
@Component
public class ElkExecutor {
    @Autowired
    @Lazy
    private BobCodyBot bobCodyBot;
    @Value("${elk.path}")
    private String elkPath;
    @Value("${izhmain.chat.id}")
    private String mainChatId;

    private SendAnimation getElkFromTelegramFile() {
        InputFile inputFile = new InputFile(elkPath);
        SendAnimation elkFriday = new SendAnimation();
        elkFriday.setAnimation(inputFile);
        return elkFriday;
    }

    public void executeFriday(String chatId) {
        try {
            log.info("try to send elk-gif");
            SendAnimation elkFriday = getElkFromTelegramFile();
            elkFriday.setChatId(chatId);
            bobCodyBot.execute(elkFriday);
        } catch (TelegramApiException e) {
            log.error("send elk as telegram file is failure, try to re-send as conventional file");
            executeFridayFromFile(chatId);
            log.error("send from file have been successful");
        }
    }


    @Scheduled(cron = "0 00 8 * * FRI ")
    private void sendFridayGif() {
        executeFriday(mainChatId);
    }

    private void executeFridayFromFile(String chatId) {
        try {
            SendAnimation elkFriday = getAnimationFromFile("elk.mp4");
            elkFriday.setChatId(chatId);
            bobCodyBot.execute(elkFriday);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("send elkFromFile is failure");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("there was some url exception");
        }
    }

    private SendAnimation getAnimationFromFile(String fileInResourcesName) throws URISyntaxException {
        URL urlElk = ClassLoader.getSystemResource(fileInResourcesName);
        File file = new File(urlElk.toURI());
        InputFile inputFile = new InputFile(file);
        SendAnimation elkFriday = new SendAnimation();
        elkFriday.setAnimation(inputFile);
        return elkFriday;
    }

}
