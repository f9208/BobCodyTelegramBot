package ru.multibot.bobcody.controller.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.SQL.Entities.BoobsStorage;
import ru.multibot.bobcody.controller.SQL.Servies.BoobsStorageServiceImp;

import java.util.NoSuchElementException;
import java.util.Random;

@Component
@Setter
@Getter
public class BoobsStorageHandler {
    @Autowired
    BoobsStorageServiceImp boobsStorageServiceImp;

    private String getById(Long id) throws NoSuchElementException {

        String result = boobsStorageServiceImp.getById(id);

        return result;
    }

    public void addBoobsLink(String link) {
        BoobsStorage bs = new BoobsStorage();
        bs.setLink(link);
        boobsStorageServiceImp.add(bs);
    }

    private String getRandom() {
        Random r = new Random();
        // автобоксинг че то не смог
        int k = Math.toIntExact(boobsStorageServiceImp.getSizeDB());
        return this.getById((long) r.nextInt(k) + 1);
    }

    public String getAnyBoobs(String inputTextMessage) {

        String[] inputTextMessageAsArray = inputTextMessage.split(" ");
        if (inputTextMessageAsArray.length == 1) {
            return getRandom();
        } else if (inputTextMessageAsArray.length > 1) {
            try {
                return getById(Long.valueOf(inputTextMessageAsArray[1]));
            } catch (NoSuchElementException e) {
                return "нету таких сисек. иди на вротмненоги.жж";
            }
        } else
            return "тебе еще 18ти нет";
    }

}
