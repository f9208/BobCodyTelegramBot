package ru.bobcody.thirdpartyapi.openweathermap.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Snow {
    @JsonProperty("3h")
    private double mm;

    public void setMm(double mm) {
        this.mm = mm;
    }

    public double getMm() {
        return mm;
    }

    @Override
    public String toString() {
        return "" + mm + " мм снега за 3 часа";
    }
}