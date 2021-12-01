package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.thirdPartyAPI.courses.CourseValutParser;

import java.util.List;

@Component
public class CourseHandler implements SimpleHandlerInterface {
    @Autowired
    private CourseValutParser courseValutParser;
    @Value("${course.command}")
    private List<String> commands;

    String getCourse() {
        StringBuilder result = new StringBuilder("текущий курс валют по курсу ЦБ РФ на ");
        double hryvnia = Double.parseDouble(courseValutParser.getValutaByCharCode("UAH").getValue()) / 10;
        double lira = Double.parseDouble(courseValutParser.getValutaByCharCode("TRY").getValue()) / 10;
        result.append(courseValutParser.getDate() + ":\n")
                .append("бакс СШП: ")
                .append(courseValutParser.getValutaByCharCode("USD").getValue().substring(0, 5))
                .append("\n")
                .append("евро: ")
                .append(courseValutParser.getValutaByCharCode("EUR").getValue().substring(0, 5))
                .append("\n")
                .append("грывна: ")
                .append(Math.ceil(hryvnia * 100) / 100)
                .append("\n")
                .append("индейка лир: ")
                .append(Math.ceil(lira * 100) / 100)
                .append("\n")
                .append("юань: ").append(courseValutParser.getValutaByCharCode("CNY").getValue().substring(0, 5))
                .append("\nнефть не нужна, собирай шишки.");
        return result.toString();
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if ("!курс".equals(inputMessage.getText()))
            result.setText(getCourse());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
