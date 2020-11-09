package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.multibot.bobcody.BobCodyBot;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.Servies.GuestServiceImp;
import ru.multibot.bobcody.controller.handlers.*;

import java.util.ArrayList;
import java.util.List;

/**
 * в метод handle() прилетают вообще _все_ входящие сообщения из чата.
 * и в зависимости от содержания текстовой составляющей (наличия команд)
 * срабатывает тот или иной обработчик.
 * практически для каждой команды сделан свой класс-обрабочик.
 * дадада, забор из if'ов, но пока хз как сделать красивее
 */

@Setter
@Getter
@Component
@PropertySource(value = {"classpath:mainHandlerChatId.properties"})
@ConfigurationProperties(prefix = "mainchatid")
public class IRCMainHandlerTextMessage implements InputTextMessageHandler {
    @Autowired
    BobCodyBot bobCodyBot;
    @Autowired
    WeatherForecastHandler weatherForecastHandler;
    @Autowired
    FuckingGreatAdviceHandler fuckingGreatAdviceHandler;
    @Autowired
    GuestServiceImp guestServiceImp;
    @Autowired
    QuoteAbyssHandler quoteAbyssHandler;
    @Autowired
    QuoteStorageHandler quoteStorageHandler;
    @Autowired
    BoobsStorageHandler boobsStorageHandler;
    @Autowired
    CourseHandler courseHandler;
    @Autowired
    OneTwoThree oneTwoThree;
    @Autowired
    HelpReplayHandler helpReplayHandler;
    @Autowired
    SlapHandler slapHandler;
    @Autowired
    List<Guest> guestList;

    List<Long> addedid;

    @Override
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

        if (textMessage.contains("bobcodybot") ||
                textMessage.contains(" bot") ||
                textMessage.contains(" bob") ||
                textMessage.contains("бобби") ||
                textMessage.contains(" бот")
                ) {
            result.setText("@" + inputMessage.getFrom().getUserName() + ", " + slapHandler.getRandomAnswer());
        }

        if (textMessage.startsWith("!погода") ||
                textMessage.startsWith("!w") ||
                textMessage.startsWith("!п") ||
                textMessage.startsWith("!g")
                ) {
            result.setText(weatherForecastAnswer(inputMessage)).setReplyToMessageId(inputMessage.getMessageId());
        }

        if (textMessage.contains("amd") || textMessage.contains("амд")) {
            result.setText(amdSucks(inputMessage));
        }
        if (textMessage.equals("!хелп") ||
                textMessage.equals("!help") ||
                textMessage.equals("!помощь") ||
                textMessage.equals("!команды")
                ) {
            result.setText(helpReplayHandler.getHelpAnswer());
        }

        if (textMessage.equals("!обс") || textMessage.equals("!fga")) {
            result.setText("@" + inputMessage.getFrom().getUserName() + ", " + fuckingGreatAdviceHandler.getAdvice());
        }

        if (textMessage.startsWith("!дсиськи")) {
            Long boobsLinkId = boobsStorageHandler.addBoobsLink(textMessage.substring(8));
            result.setText("Сиськи добавлены (" + boobsLinkId + ")");
        }
        if (textMessage.startsWith("!сиськи") || textMessage.startsWith("!boobs")) {
            result.setText(boobsStorageHandler.getAnyBoobs(textMessage));
        }
        if (textMessage.startsWith("!дц") ||
                textMessage.startsWith("!lw") ||
                textMessage.startsWith("!aq")) {
            result.setText(quoteAbyssHandler.addQuoteToAbyss(inputMessage));
        }
        if (textMessage.trim().startsWith("!ц") ||
                textMessage.trim().startsWith("!q")) {
            result.setText(quoteStorageHandler.getQuoteStorage(inputMessage));

        }
        if (textMessage.startsWith("!курс")) {
            result.setText(courseHandler.getCourse());
        }
        if (textMessage.startsWith("123") & (textMessage.length() == 3)) {
            result.setText(oneTwoThree.getRandomPhrase());
        }
        if (textMessage.startsWith("пятница")) {
            fridayGif(inputMessage);
        }

        if (textMessage.startsWith("есть")) {
            System.out.println(quoteStorageHandler.existsById(Long.valueOf(textMessage.split(" ")[1])));
        }
        if (result != null) result.setChatId(inputMessage.getChatId());

        return result;
    }

    public List<Long> getChatIDs() {
        List result = new ArrayList();
        for (Long i : addedid) result.add(i);
        return result;
    }

    private Boolean containUserToMainTable(User user) {
        return guestServiceImp.comprise(Long.valueOf(user.getId()));
    }

    private String weatherForecastAnswer(Message message) {
        StringBuilder cityName = new StringBuilder();
        String[] cityTwoWord = message.getText().split(" ");
        if (cityTwoWord.length == 1
                && (cityTwoWord[0].equals("!g") || cityTwoWord[0].equals("!w")
                || cityTwoWord[0].equals("!п")
                || cityTwoWord[0].equals("!погода"))) {
            cityName.append("default");
            return weatherForecastHandler.getForecast(cityName.toString());
        }
        for (int i = 1; i < cityTwoWord.length; i++) {
            cityName.append(cityTwoWord[i]);
        }

        if (cityName.length() != 0) return weatherForecastHandler.getForecast(cityName.toString());
        else return null;
    }

    @Scheduled(cron = "0 0 8 * * FRI ")
    public void se() {
        try {
            bobCodyBot.execute(new SendAnimation()
                    .setAnimation("CgACAgIAAxkBAAIOU19X2Fq4QYnUI15KI4h8MAYj4_WGAAJjCQACE5zBShCPD2aK8whrGwQ")
                    .setChatId("-458401902"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void fridayGif(Message message) {
        try {
            bobCodyBot.execute(new SendAnimation().setAnimation("CgACAgIAAxkBAAIOU19X2Fq4QYnUI15KI4h8MAYj4_WGAAJjCQACE5zBShCPD2aK8whrGwQ")
                    .setChatId(message.getChatId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String amdSucks(Message message) {
        String[] textMessageAsArray = message.getText().split(" ");
        String result = null;
        for (String a : textMessageAsArray) {
            if (a.equals("AMD") ||
                    a.equals("amd") ||
                    a.equals("амд") ||
                    a.equals("АМД")) result = "@" + message.getFrom().getUserName() + ", AMD сосет";
        }
        return result;
    }

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


