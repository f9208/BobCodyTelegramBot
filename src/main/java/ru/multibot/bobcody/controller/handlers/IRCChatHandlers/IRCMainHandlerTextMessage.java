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
import ru.multibot.bobcody.Service.FuckingGreatAdvice.FuckingGreatAdvice;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.Entities.QuotationsBook;
import ru.multibot.bobcody.controller.SQL.Servies.GuestServiceImp;
import ru.multibot.bobcody.Service.weather.OpenWeatherForecast;
import ru.multibot.bobcody.controller.SQL.Servies.QuotationsBookServiceImp;
import ru.multibot.bobcody.controller.handlers.InputTextMessageHandler;
import ru.multibot.bobcody.controller.handlers.QuoteHandler;

import java.io.IOException;
import java.util.Random;

//import ru.multibot.bobcody.controller.SQL.Entities.Guest;

@Setter
@Getter
@Component
@PropertySource(value = "classpath:helpFile.properties", encoding = "UTF-16")
@ConfigurationProperties(prefix = "allowedchatid")
public class IRCMainHandlerTextMessage implements InputTextMessageHandler {
    @Autowired
    OpenWeatherForecast openWeatherForecast;
    @Autowired
    FuckingGreatAdvice fuckingGreatAdvice;
    @Autowired
    GuestServiceImp guestServiceImp;
    @Autowired
    QuoteHandler quoteHandler;

    Long asf;
    String[] slapAnswer = {"хули надо?",
            "по голове себе посутчи",
            "мамке скажи что я зайду",
            "321",
            "Таня нагнулась - в жопе топор. \nМетко кидает индеец Егор."};
    @Value("${print.help}")
    private String help;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = null;
        String textMessage = inputMessage.getText();

        User user = inputMessage.getFrom();
        if (user != null && !containUserToMainTable(user)) addToMainDataBase(user);

        if (textMessage.contains("BobCodyBot") ||
                textMessage.contains("bot") ||
                textMessage.contains("Bob")) {
            result = slapAnswer(inputMessage);
        }

        if (textMessage.startsWith("!погода") ||
                textMessage.startsWith("!Погода") ||
                textMessage.startsWith("!w") ||
                textMessage.startsWith("!W") ||
                textMessage.startsWith("!п") ||
                textMessage.startsWith("!П") ||
                textMessage.startsWith("!П") ||
                textMessage.startsWith("!g")
                ) {
            result = weatherForecastAnswer(inputMessage);
        }

        if (textMessage.contains("АМД") ||
                textMessage.contains("AMD") ||
                textMessage.contains("amd") ||
                textMessage.contains("амд")) {
            System.out.println("amd");
            result = amdSuck(inputMessage);
        }
        if (textMessage.equals("!хелп") ||
                textMessage.equals("!Хелп") ||
                textMessage.equals("!Help") ||
                textMessage.equals("!help") ||
                textMessage.equals("!Команды") ||
                textMessage.equals("!команды")
                ) {
            result = helpAnswer(inputMessage);
        }

        if (textMessage.equals("!обс") ||
                textMessage.equals("!ОБС")) {
            result = fga();
        }

        if (textMessage.startsWith("!add")) {
            addToMainDataBase(user);

        }

        if (textMessage.startsWith("!дц") ||
                textMessage.startsWith("!lw") ||
                textMessage.startsWith("!aq")) {
            result = quoteHandler.addQuote(inputMessage);
        }

        if (textMessage.startsWith("!уц"))
            result = quoteHandler.deleteQuote(inputMessage);
        if (textMessage.startsWith("!ц ") || textMessage.startsWith("!q ")) {
            quoteHandler.randomQuote(inputMessage);
        }

        if (result != null) result.setChatId(inputMessage.getChatId());
        return result;
    }


    private Boolean containUserToMainTable(User user) {
        return guestServiceImp.comprise(Long.valueOf(user.getId()));

    }

    @Override
    public Long getChatID() {
        return asf;
    }

    private SendMessage slapAnswer(Message message) {
        SendMessage replay = new SendMessage();
        Random m = new Random(message.getMessageId());
        replay.setText(slapAnswer[m.nextInt(slapAnswer.length)]);
        return replay;
    }

    private SendMessage weatherForecastAnswer(Message message) {
        SendMessage result = new SendMessage();
        StringBuilder cityName = new StringBuilder();
        String[] cityTwoWord = message.getText().split(" ");
        if (cityTwoWord.length == 1) cityName.append("Ижевск");
        for (int i = 1; i < cityTwoWord.length; i++) {
            cityName.append(cityTwoWord[i]);
        }
        try {
            result.setReplyToMessageId(message.getMessageId()).setText(openWeatherForecast.getForecast(cityName.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            result.setText(cityName + "? Где это? в Бельгии что-ли?");
        }
        return result;
    }

    private SendMessage amdSuck(Message message) {
        SendMessage result = new SendMessage();
        String[] textMessageAsArray = message.getText().split(" ");
        String answer = null;
        System.out.println(message.getFrom().getUserName());

        for (String a : textMessageAsArray) {
            if (a.equals("AMD") ||
                    a.equals("amd") ||
                    a.equals("амд") ||
                    a.equals("АМД")) answer = message.getFrom().getUserName() + ", AMD сосет";
            result.setText(answer);
        }
        return result;
    }

    private SendMessage helpAnswer(Message message) {
        SendMessage result = new SendMessage();
        return result.setText(help);
    }

    private SendMessage fga() {
        String textAnswer;
        try {
            textAnswer = fuckingGreatAdvice.getAdvice();
        } catch (IOException e) {
            textAnswer = "че то сервис не алё. совет от бота - не еби, блять, мозги!";
        }
        return new SendMessage().setText(textAnswer);
    }

    private void addToMainDataBase(User user) {
        Guest guest = new Guest(user);
        if (!containUserToMainTable(user)) guestServiceImp.add(guest);
        else System.out.println("такой юзер уже содержится в ДБ");

    }
}


