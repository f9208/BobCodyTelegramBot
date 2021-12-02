package ru.bobcody.controller.handlers.chatHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MainHandlerTextMessage {
    @Value("${slap.command}")
    private List<String> slapList;
    @Value("${amd.command}")
    private List<String> amdList;
    private Map<String, SimpleHandlerInterface> multiHandler = new HashMap<>();

    public MainHandlerTextMessage(List<SimpleHandlerInterface> handlers) {
        for (SimpleHandlerInterface iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                multiHandler.put(insideListOrder, iterHandler);
            }
        }
    }

    public SendMessage handle(Message message) {
        SendMessage result = new SendMessage();
        if (result.getChatId() == null) result.setChatId(message.getChatId().toString());

        String textMessage = message.getText().toLowerCase();
        if (multiHandler.containsKey(textMessage.split(" ")[0])) {
            result = multiHandler.get(textMessage.split(" ")[0]).handle(message);
            if (result.getChatId() == null) result.setChatId(message.getChatId().toString());
            return result;
        }
        String slapAnswer;
        if (!(slapAnswer = findCommandInside(message)).isEmpty()) {
            result.setText(slapAnswer);
            return result;
        }

        if ("!ссылки".equals(textMessage) ||
                " !ссылки".equals(textMessage) ||
                "!ссылки ".equals(textMessage)) {
            result.setText("ссылки бабая: https://t.me/izhmain/107384");
        }
        return result;
    }

    /**
     * Поиск команд обычно идет в мапе multiHandler, ключом к которой есть первое слово сообщения
     * но чтобы включить слапы бота и реакции на "амд" - надо пробежаться по всем словам сообщения,
     * и отреагировать в случае нахождения слов-команд
     */
    private String findCommandInside(Message message) {
        String[] singleWordArray = message.getText().split("[{^?!*+ .,$:;#%/|()]");
        Set<String> setUniqWords = Arrays.stream(singleWordArray).collect(Collectors.toSet());
        int slapCall = setUniqWords.stream().filter(slapList::contains).collect(Collectors.toSet()).size();
        int amdCall = setUniqWords.stream().filter(amdList::contains).collect(Collectors.toSet()).size();
        if (slapCall != 0) return multiHandler.get("бот").handle(message).getText();
        if (amdCall != 0) return multiHandler.get("amd").handle(message).getText();
        return "";
    }
}