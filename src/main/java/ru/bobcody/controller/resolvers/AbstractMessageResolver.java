package ru.bobcody.controller.resolvers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


abstract public class AbstractMessageResolver {
    abstract SendMessage process(Message message);
}
