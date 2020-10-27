package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.multibot.bobcody.Services.courses.CourseValutParser;

@Component
@Getter
@Setter
public class CourseHandler {
    @Autowired
    CourseValutParser courseValutParser;

    public String getCourse() {
        StringBuilder result = new StringBuilder("текущий курс валют по курсу ЦБ РФ на ");
        Double grivna = Double.valueOf(courseValutParser.getValuteByCharCode("UAH").getValue());
        grivna = grivna / 10;
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
                .append("юань: ").append(courseValutParser.getValuteByCharCode("CNY").getValue().substring(0, 5))
                .append("\nнефть не нужна, собирай шишки.");

        return result.toString();

    }

}
