package ru.multibot.bobcody.Services.HotPies;

import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class SinglePie {
    String textPieItself;
    String linkToPie;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(textPieItself).append("\n").append(linkToPie.substring(7));
        return result.toString();
    }

    public List<SinglePie> getPiesList() {
        System.out.println("обновили лист");
        Random r = new Random();
        int numberOfPage = r.nextInt(3100);
        List<SinglePie> result = new ArrayList<>();
        SinglePie failed = new SinglePie();
        System.out.println("номер страницы: " + numberOfPage);
        failed.setTextPieItself("сегодня пирожков нет");
        try {
            result = new PiesParser().gelListPies(numberOfPage);
            result.addAll(new PiesParser().gelListPies(numberOfPage + 1));
        } catch (Exception e) {
            result.add(failed);
        }
        return result;
    }
}