package ru.multibot.bobcody.Services.HotPies;

import lombok.Getter;
import lombok.Setter;

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
}