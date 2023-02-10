package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;

public class SendElkCommand extends AbstractCommand {

    @Autowired
    private BobCodyBot bobCodyBot;

    private final String chatId;

    public SendElkCommand(String chatId) {
        this.chatId = chatId;
    }

    @Override
    public String execute() {
        SendAnimation elkFridayAnimation = new SendAnimation();
        elkFridayAnimation.setAnimation(new InputFile(settingService.getElkPath()));

        elkFridayAnimation.setChatId(chatId);

        try {
            bobCodyBot.execute(elkFridayAnimation);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
