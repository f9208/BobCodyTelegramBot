package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.multibot.bobcody.BobCodyBot;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.Servies.GuestServiceImp;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler.SlapHandler;

import java.util.HashMap;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Setter
@Getter
@Component
@PropertySource(value = {"classpath:mainHandlerChatId.properties"})
@ConfigurationProperties(prefix = "mainchatid")
public class IRCMainHandlerTextMessage {
    @Autowired
    BobCodyBot bobCodyBot;
    @Autowired
    GuestServiceImp guestServiceImp;
    @Autowired
    SlapHandler slapHandler;
    @Autowired
    List<Guest> guestList;

    private Map<String, SimpleHandlerInterface> multihandler = new HashMap<>();

    List<Long> addedid;

    public IRCMainHandlerTextMessage(List<SimpleHandlerInterface> handlers) {
        for (SimpleHandlerInterface iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                multihandler.put(insideListOrder, iterHandler);
            }
        }
    }

    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String textMessage = inputMessage.getText().toLowerCase();
        User user = inputMessage.getFrom();
        //добавляем юзера в базу юзеров. just in case

        if (guestList.size() == 0) {
            //обнови set с базы
            System.out.println("кэш базы пользователей пуст. обновляем");
            reloadUsersList();
        }
        if (user != null && !containGuestInList(user)) {
            addToMainDataBase(user);
            reloadUsersList();
        }

        if (multihandler.containsKey(textMessage.split(" ")[0])) {
            result = multihandler.get(textMessage.split(" ")[0]).handle(inputMessage);
            if (result != null && result.getChatId() == null)
                //если нашли какую то команду и обработчик к ней то на остальные условия можно положить
                return result.setChatId(inputMessage.getChatId());
        }

        if (touchBotName(textMessage)) {
            result = multihandler.get("бот").handle(inputMessage);
        }

        if (textMessage.contains("amd ")
                || textMessage.contains("амд ")
                || textMessage.endsWith(" амд")
                || textMessage.endsWith(" amd")) {
            result = multihandler.get("amd").handle(inputMessage);
        }

        if (textMessage.equals("!ссылки") ||
                textMessage.equals(" !ссылки") ||
                (textMessage.equals("!ссылки "))) {
            result.setText("ссылки бабая: https://t.me/izhmain/107384");
        }

        if (result != null && result.getChatId() == null) result.setChatId(inputMessage.getChatId());

        return result;
    }

    private Boolean containUserToMainTable(User user) {
        return guestServiceImp.comprise(Long.valueOf(user.getId()));
    }

    private boolean touchBotName(String text) {
        boolean result = false;
        String[] singleWordArray = text.split("[{^?*+ .,$:;#%/|()]");
        for (String oneWOrd : singleWordArray) {
            if (oneWOrd.equals("бот") ||
                    oneWOrd.equals("bob") ||
                    oneWOrd.equals("bot") ||
                    oneWOrd.equals("b0t") ||
                    oneWOrd.equals("@bobcodybot") ||
                    oneWOrd.equals("боб") ||
                    oneWOrd.equals("бобу") ||
                    oneWOrd.equals("бобби") ||
                    oneWOrd.equals("b0b")
                    ) result = true;
        }
        return result;
    }

//    private String amdSucks(Message inputMessage) {
//        String[] textMessageAsArray = inputMessage.getText().split("[{^?*+ .,$:;#%/|()]");
//        String result = null;
//        for (String a : textMessageAsArray) {
//            if (a.equals("AMD") ||
//                    a.equals("amd") ||
//                    a.equals("амд") ||
//                    a.equals("АМД")) result = "@" + inputMessage.getFrom().getUserName() + ", AMD сосет";
//        }
//        return result;
//    }

    private void addToMainDataBase(User user) {
        Guest guest = new Guest(user);
        if (!containUserToMainTable(user)) {
            guestServiceImp.add(guest);
        } else {
            System.out.println("такой юзер уже содержится в ДБ");
            reloadUsersList();
        }
    }

    private void reloadUsersList() {
        guestList = guestServiceImp.getAllGuests();
    }

    private boolean containGuestInList(User user) {
        Guest guest = new Guest(user);

        return guestList.contains(guest);
    }
}


