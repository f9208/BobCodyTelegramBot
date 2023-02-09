package ru.bobcody.thirdpartyapi.openweathermap.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class Snow {
    @JsonProperty("3h")
    private double mm;

    @Override
    public String toString() {
        return "" + mm + " мм снега за 3 часа";
    }
}