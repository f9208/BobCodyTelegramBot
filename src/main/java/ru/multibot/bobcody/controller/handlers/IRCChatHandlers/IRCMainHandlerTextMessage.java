package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.Servies.GuestServiceImp;
import ru.multibot.bobcody.controller.handlers.*;

import java.util.List;
import java.util.Random;

@Setter
@Getter
@Component
@PropertySource(value = {"classpath:allowedchatid.properties"})
@ConfigurationProperties(prefix = "chatid")
public class IRCMainHandlerTextMessage implements InputTextMessageHandler {
    @Autowired
    WeatherForecastHandler weatherForecastHandler;
    @Autowired
    FuckingGreatAdviceHandler fuckingGreatAdviceHandler;
    @Autowired
    GuestServiceImp guestServiceImp;
    @Autowired
    QuoteHandler quoteHandler;
    @Autowired
    QuoteBookHandler quoteBookHandler;
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

    List<Long> asf;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String textMessage = inputMessage.getText().toLowerCase();

        User user = inputMessage.getFrom();
        //добавляем юзера в базу юзеров. just in case
        if (user != null && !containUserToMainTable(user)) addToMainDataBase(user);

        if (textMessage.contains("bobcodybot") ||
                textMessage.contains("bot") ||
                textMessage.contains("bob") ||
                textMessage.contains("бот")
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
            result.setText(fuckingGreatAdviceHandler.getAdvice());
        }

        if (textMessage.startsWith("!дсиськи")) {
            boobsStorageHandler.addBoobsLink(textMessage.substring(8));
            result.setText("Сиськи добавлены");
        }
        if (textMessage.startsWith("!сиськи") || textMessage.startsWith("!boobs")) {
            result.setText(boobsStorageHandler.getAnyBoobs(textMessage));
        }
        if (textMessage.startsWith("!дц") ||
                textMessage.startsWith("!lw") ||
                textMessage.startsWith("!aq")) {
            result.setText(quoteHandler.addQuote(inputMessage));
        }
        if (textMessage.trim().startsWith("!ц") ||
                textMessage.trim().startsWith("!q")) {
            result.setText(quoteBookHandler.getQuoteBook(inputMessage));

        }
        if (textMessage.startsWith("!курс")) {
            result.setText(courseHandler.getCourse());
        }
        if (textMessage.startsWith("123") & (textMessage.length() == 3)) {
            result.setText(oneTwoThree.getRandomPhrase());
        }
        if (result != null) result.setChatId(inputMessage.getChatId());
        return result;
    }


    private Boolean containUserToMainTable(User user) {
        return guestServiceImp.comprise(Long.valueOf(user.getId()));

    }

    @Override
    public Long getChatID() {
        return asf.get(0); // нулевой - это индекс чата izhMain.
    }


    private String weatherForecastAnswer(Message message) {
        String result;
        StringBuilder cityName = new StringBuilder();
        String[] cityTwoWord = message.getText().split(" ");
        if (cityTwoWord.length == 1) cityName.append("default");
        for (int i = 1; i < cityTwoWord.length; i++) {
            cityName.append(cityTwoWord[i]);
        }
        result = weatherForecastHandler.getForecast(cityName.toString());
        return result;
    }

    private String amdSucks(Message message) {
        String[] textMessageAsArray = message.getText().split(" ");
        String result = null;
        for (String a : textMessageAsArray) {
            if (a.equals("AMD") ||
                    a.equals("amd") ||
                    a.equals("амд") ||
                    a.equals("АМД")) result = message.getFrom().getUserName() + ", AMD сосет";
        }
        return result;
    }

    private void addToMainDataBase(User user) {
        Guest guest = new Guest(user);
        if (!containUserToMainTable(user)) guestServiceImp.add(guest);
        else System.out.println("такой юзер уже содержится в ДБ");

    }
}


