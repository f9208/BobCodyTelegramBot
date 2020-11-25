package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface SimpleHandlerInterface {
    SendMessage handle(Message inputMessage);
    List<String> getOrderList();
}
