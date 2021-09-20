package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.thirdPartyAPI.courses.CourseValutParser;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class CourseHandler implements SimpleHandlerInterface {
    @Autowired
    CourseValutParser courseValutParser;

    private String getCourse() {
        StringBuilder result = new StringBuilder("текущий курс валют по курсу ЦБ РФ на ");
        Double grivna = Double.valueOf(courseValutParser.getValuteByCharCode("UAH").getValue()) / 10;
        Double lira = Double.valueOf(courseValutParser.getValuteByCharCode("TRY").getValue()) / 10;
        result.append(courseValutParser.getDate() + ":\n")
                .append("бакс СШП: ")
                .append(courseValutParser.getValuteByCharCode("USD").getValue().substring(0, 5))
                .append("\n")
                .append("гейро: ")
                .append(courseValutParser.getValuteByCharCode("EUR").getValue().substring(0, 5))
                .append("\n")
                .append("грывна: ")
                .append(String.valueOf(Math.ceil(grivna * 100) / 100))
                .append("\n")
                .append("индейка лир: ")
                .append(String.valueOf(Math.ceil(lira * 100) / 100))
                .append("\n")
                .append("юань: ").append(courseValutParser.getValuteByCharCode("CNY").getValue().substring(0, 5))
                .append("\nнефть не нужна, собирай шишки.");

        return result.toString();

    }

    @Override
    public SendMessage handle(Message inputMessage) {

        SendMessage result = new SendMessage();
        //отправляем только на  команду !курс
        if (inputMessage.getText().length() == 5)
            result.setText(getCourse());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!курс");
        return commands;
    }
}
