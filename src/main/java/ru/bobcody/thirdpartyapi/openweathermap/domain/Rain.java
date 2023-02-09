package ru.bobcody.thirdpartyapi.openweathermap.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
public class Rain {
    @JsonProperty("3h")
    private double mm;

    @Override
    public String toString() {
        return "" + mm + " мм воды за 3 часа";
    }
}
