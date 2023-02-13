package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bobcody.BobCodyBot;

public class ForceSendMessageCommand extends AbstractCommand {
    @Autowired
    private BobCodyBot bobCodyBot;
    private String chatDestinationId;
    private String textMessage;

    public ForceSendMessageCommand(String chatDestinationId, String textMessage) {
        this.chatDestinationId = chatDestinationId;
        this.textMessage = textMessage;
    }

    @Override
    public <R> R execute() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(textMessage);
        sendMessage.setChatId(chatDestinationId);
        try {
            bobCodyBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
        return null;
    }
}
