package ru.multibot.bobcody.ThirdPartyAPI.HotPies;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * класс, который образует единичный пирожок, распарсенный классом PiesParser
 * метод getPiesList() возвращает список из 60ти пирожков,
 * со страницы рендомного и рендом+1 номера
 */
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
        Random r = new Random();
        int numberOfPage = r.nextInt(3100);
        List<SinglePie> result = new ArrayList<>();
        SinglePie failedCase = new SinglePie();
        failedCase.setTextPieItself("сегодня пирожков нет. все съели. шутка, не работает что-то");
        try {
            result = new PiesParser().gelListPies(numberOfPage);
            result.addAll(new PiesParser().gelListPies(numberOfPage + 1));
        } catch (Exception e) {
            result.add(failedCase);
        }
        return result;
    }
}