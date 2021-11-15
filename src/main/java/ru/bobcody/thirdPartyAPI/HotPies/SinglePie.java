package ru.bobcody.thirdPartyAPI.HotPies;

import lombok.Getter;
import lombok.Setter;

/**
 * класс, который образует единичный пирожок, распарсенный классом PiesParser
 * метод getPiesList() возвращает список из 60ти пирожков,
 * со страницы рендомного и рендом+1 номера
 */
@Getter
@Setter
public class SinglePie {
    String shareURL;
    String shareText;

    @Override
    public String toString() {
        return shareText + "\n" + shareURL;
    }
}