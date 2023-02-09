package ru.bobcody.thirdpartyapi.openweathermap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIgnoreProperties(value = {"temp_min", "temp_max", "humidity", "feels_like", "pressure", "sea_level", "grnd_level", "temp_kf"})
public class Main {
    private int temp;

    public void setTemp(double temp) {
        this.temp = (int) Math.round(temp);
    }

    public int getTemperature() {
        return temp;
    }
}