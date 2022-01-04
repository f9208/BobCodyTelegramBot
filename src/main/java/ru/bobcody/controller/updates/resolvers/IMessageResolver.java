package ru.bobcody.controller.updates.resolvers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


interface IMessageResolver {
    SendMessage process(Message message);
}