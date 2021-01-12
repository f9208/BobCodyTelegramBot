package ru.multibot.bobcody.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

@Component
@Getter
@Setter
public class FloodControll {

    private static final Long STARTTIME = System.currentTimeMillis() / 1000;
    private Message[] filterBuffer = new Message[4];

    boolean allowed = true;

    public void filter(Message inputMessage) {
        add(inputMessage);
        allowed = choise();
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(10*1000);
                allowed=true;
            }
        }).start();
    }

    private void add(Message inputMessage) {
        System.arraycopy(filterBuffer, 0, filterBuffer, 1, 3);
        filterBuffer[0] = inputMessage;
    }

    public boolean choise() {
        if (filterBuffer[0] != null
                && filterBuffer[3] != null
                && filterBuffer[0].getFrom() == filterBuffer[3].getFrom()) {
            if (filterBuffer[0].getDate() - filterBuffer[3].getDate() < 10) return false;
        }
        return true;
    }
}

