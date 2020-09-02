package ru.multibot.bobcody.controller.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.multibot.bobcody.controller.SQL.Entities.BoobsStorage;
import ru.multibot.bobcody.controller.SQL.Servies.BoobsStorageService;
import ru.multibot.bobcody.controller.SQL.Servies.BoobsStorageServiceImp;

import java.util.NoSuchElementException;

@Component
@Setter
@Getter
public class BoobsStorageHandler {
    @Autowired
    BoobsStorageServiceImp boobsStorageServiceImp;

    public String getById(Long id) {
        String result;
        try {
            result = boobsStorageServiceImp.getById(id);
        } catch (NoSuchElementException e) {
            result= "сисек сегодня не будет";
        }
        return result;
    }

    public void addBoobsLink(String link) {
        BoobsStorage bs=new BoobsStorage();
        bs.setLink(link);
        boobsStorageServiceImp.add(bs);
    }
}
