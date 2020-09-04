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
@PropertySource(value = {"classpath:helpFile.properties"}, encoding = "UTF-16")
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
    List<Long> asf;
    String[] slapAnswer = {"хули надо?",
            "по голове себе постучи",
            "мамке скажи что я зайду",
            "Таня нагнулась - в жопе топор. \nМетко кидает индеец Егор.",
            "изыди, онимешнег!",
            "забанить бы тебя да шотгана-кикалки нет"
    };
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
            result = amdSucks(inputMessage);
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
        if (textMessage.startsWith("!дсиськи")) {
            boobsStorageHandler.addBoobsLink(textMessage.substring(8));
            result = new SendMessage().setText("Сиськи добавлены");
        }

        if (textMessage.startsWith("!дц") ||
                textMessage.startsWith("!lw") ||
                textMessage.startsWith("!aq")) {
            result = quoteHandler.addQuote(inputMessage);
        }
        if (textMessage.trim().startsWith("!ц") ||
                textMessage.trim().startsWith("!q")) {
            result = quoteBookHandler.getQuoteBook(inputMessage);

        }
        if (textMessage.startsWith("!курс")) {
            result = new SendMessage().setText(courseHandler.getCourse());
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
        result.setReplyToMessageId(message.getMessageId()).setText(weatherForecastHandler.getForecast(cityName.toString()));
        return result;
    }

    private SendMessage amdSucks(Message message) {
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
        return new SendMessage().setText(fuckingGreatAdviceHandler.getAdvice());
    }

    private void addToMainDataBase(User user) {
        Guest guest = new Guest(user);
        if (!containUserToMainTable(user)) guestServiceImp.add(guest);
        else System.out.println("такой юзер уже содержится в ДБ");

    }
}


