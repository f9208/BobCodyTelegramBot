package ru.multibot.bobcody.ThirdPartyAPI.weather.weatherCondition;

public class Wind {
    private int speed;
    private String degDirection;
    private int deg;
    private String[] directionString = {
            "С", "СВ", "В", "ЮВ", "Ю", "ЮЗ", "З", "СЗ", "С"
//            "северный",
//            "северо-восточный",
//            "восточный",
//            "юго-восточный",
//            "южный",
//            "юго-западный",
//            "западный",
//            "северо-западный",
//            "северный"
    };

    public void setSpeed(double speed) {
        this.speed = (int) Math.round(speed);
    }

    public void setDegDirection(int deg) {
        double subDeg = (double) deg;
        this.degDirection = directionString[(int) Math.round(subDeg / 45)];
    }

    public void setDeg(int deg) {
        this.deg = deg;
        setDegDirection(deg);

    }

    public String getDegDirection() {
        return degDirection;
    }

    public double getSpeed() {
        return speed;
    }

    public int getDeg() {

        return deg;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ветер ").append(degDirection).append(", ")
                .append(getSpeed()).append(" м/c");
        return result.toString();
    }
}