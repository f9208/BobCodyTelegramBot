package ru.bobcody.thirdpartyapi.openweathermap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"gust"})
public class Wind {
    private int speed;
    private int deg;

    private String directionDegree;
    private final static String[] DIRECTION_SIDE = {
            "С", "СВ", "В", "ЮВ", "Ю", "ЮЗ", "З", "СЗ", "С"};

    public void setSpeed(double speed) {
        this.speed = (int) Math.round(speed);
    }

    public void setDeg(int deg) {
        this.deg = deg;
        this.directionDegree = DIRECTION_SIDE[deg / 45];
    }

    @Override
    public String toString() {
        return "ветер " + directionDegree + ", " + speed + " м/с";
    }
}