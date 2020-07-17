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
import ru.multibot.bobcody.Service.FuckingGreatAdvice.FuckingGreatAdvice;
import ru.multibot.bobcody.Service.weather.OpenWeatherForecast;
import ru.multibot.bobcody.controller.SQL.AddUser;
import ru.multibot.bobcody.controller.handlers.InputTextMessageHandler;

import java.io.IOException;
import java.util.Random;

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
    Long asf;
    String[] slapAnswer = {"хули надо?",
            "по голове себе посутчи",
            "мамку свою спроси",
            "321",
            "Таня нагнулась - в жопе топор. \nМетко кидает индеец Егор."};
    @Value("${print.help}")
    private String help;

    @Override
    public SendMessage handle(Message message) {
        SendMessage result = null;
        String textMessage = message.getText();
        if (textMessage.contains("BobCodyBot") ||
                textMessage.contains("bot") ||
                textMessage.contains("Bob")) {
            result = slapAnswer(message);
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
            result = weatherForecastAnswer(message);
        }

        if (textMessage.contains("АМД") ||
                textMessage.contains("AMD") ||
                textMessage.contains("amd") ||
                textMessage.contains("амд")) {
            System.out.println("amd");
            result = amdSuck(message);
        }
        if (textMessage.equals("!хелп") ||
                textMessage.equals("!Хелп") ||
                textMessage.equals("!Help") ||
                textMessage.equals("!help") ||
                textMessage.equals("!Команды") ||
                textMessage.equals("!команды")
                ) {
            result = helpAnswer(message);
        }

        if (textMessage.equals("!обс")||
                textMessage.equals("!ОБС")) {
            result= fga();
        }

        if (textMessage.equals("!add")) {
            AddUser addUser=new AddUser();
//            addUser.rrr();
        }
        if (result != null) result.setChatId(message.getChatId());
        return result;
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
    private SendMessage fga(){
        String textAnswer;
        try {
            textAnswer = fuckingGreatAdvice.getAdvice();
        } catch (IOException e) {
            textAnswer= "че то сервис не алё. совет от бота - не еби, блять, мозги!";
        }
        return new SendMessage().setText(textAnswer);
    }
}
