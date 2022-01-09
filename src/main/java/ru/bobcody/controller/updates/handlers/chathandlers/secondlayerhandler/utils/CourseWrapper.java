package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bobcody.thirdpartyapi.courses.CourseValutParser;

@Component
@RequiredArgsConstructor
public class CourseWrapper {
    private final CourseValutParser courseValutParser;

    public String getCourse() {
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
}
