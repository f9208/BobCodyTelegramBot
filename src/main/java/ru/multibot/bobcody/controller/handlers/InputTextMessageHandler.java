package ru.multibot.bobcody.controller.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputTextMessageHandler {
    SendMessage handle(Message message);
    Long getChatID();
}
