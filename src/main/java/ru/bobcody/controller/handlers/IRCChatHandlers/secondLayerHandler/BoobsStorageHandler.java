package ru.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.Entities.BoobsStorage;
import ru.bobcody.Servies.BoobsStorageServiceImp;
import ru.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.util.*;

@Component
@Setter
@Getter
public class BoobsStorageHandler implements SimpleHandlerInterface {
    @Autowired
    BoobsStorageServiceImp boobsStorageServiceImp;

    public Long addBoobsLink(String link) {
        BoobsStorage bs = new BoobsStorage();
        bs.setLink(link);
        boobsStorageServiceImp.add(bs);
        return boobsStorageServiceImp.findIdByLink(bs);
    }

    private String getRandom() {
        Random r = new Random();
        List<BoobsStorage> boobsList = getDbAsList();
        int k = r.nextInt(boobsList.size());

        return boobsList.get(k).getLink();
    }

    private List<BoobsStorage> getDbAsList() {
        List<BoobsStorage> result = new ArrayList<>();
        Iterable<BoobsStorage> iterablyDB = boobsStorageServiceImp.getAllAsIterator();
        for (BoobsStorage one : iterablyDB) {
            result.add(one);
        }
        return result;
    }

    private String getLinkById(Long id) {
        return boobsStorageServiceImp.getById(id);
    }

    public String getAnyBoobs(String inputTextMessage) {
        Long anyId;
        try {
            anyId = Long.valueOf(inputTextMessage.split(" ")[1]);
            return getLinkById(anyId);

        } catch (ArrayIndexOutOfBoundsException e) {
            return "случайные: " + getRandom();
        } catch (NumberFormatException e) {
            System.out.println("неверный формат бубс-идентификатора");
            return "организм, вводи только цифры после команды";
        } catch (NoSuchElementException e) {
            System.out.println("такие сиськи не найдены");
            return "нету такой в базе. на случайную " + getRandom();
        }

    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String inputText = inputMessage.getText();
        Long boobsLinkId;
        if (inputText.startsWith("!дсиськи") && inputText.length() > 9) {
            boobsLinkId = addBoobsLink(inputText.substring(8));
            result.setText("Сиськи добавлены (" + boobsLinkId + ")");
        }
        if (inputText.startsWith("!сиськи") || inputText.startsWith("!boobs")) {
            result.setText(getAnyBoobs(inputText));
            result.setChatId(inputMessage.getFrom().getId().toString());
            return result;
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!дсиськи");
        commands.add("!сиськи");
        return commands;
    }
}