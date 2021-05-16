package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

/**
 * базовый интерфейс обработчика входящих Message-объектов.
 * в список, который возвращает getOrderList() закидываются команды,
 * на которые будет реагировать соответствующая реализация интерфейса
 * т.е. команда !пятница добавлена в список команд класса FridayHandler,
 * и т.к. он реализует этот интерфейс, он имеет свой обработчик, возвращающий
 * SendMessage соответствующей команды.
 * <p>
 * во время запуска приложения Spring собирает все реализации этого интерфейса
 * в  Map<String, SimpleHandlerInterface>, где ключ String - это какая то команда (!п, !погода
 * !обс, !время), SimpleHandlerInterface - соответствующая этой команде реализация.
 */
public interface SimpleHandlerInterface {
    SendMessage handle(Message inputMessage);

    List<String> getOrderList();
}
