package ru.multibot.bobcody.ThirdPartyAPI.weather;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

class MainComponentForecast {
    @Getter
    private int temp;
    @Getter
    @JsonProperty("feels_like")
    private int feelsLike;
    @Getter
    @JsonProperty("temp_min")
    private int tempMin;
    @Getter
    @JsonProperty("temp_max")
    private int tempMax;
    @Setter
    @Getter
    private int pressure;
    @Setter
    @Getter
    @JsonProperty("sea_level")
    private int seaLevel;
    @Setter
    @Getter
    @JsonProperty("grnd_level")
    private int groundLevel;
    @Setter
    @Getter
    private int humidity;
    @Setter
    @Getter
    @JsonProperty("temp_kf")
    private double tempKf;

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = (int) Math.round(feelsLike);
    }

    public void setTemp(double temp) {
        this.temp = (int) Math.round(temp);
    }

    public void setTempMin(double tempMin) {
        this.tempMin = (int) Math.round(tempMin);
    }

    public void setTempMax(double tempMax) {
        this.tempMax = (int) Math.round(tempMax);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("t ").append(temp).append("\u2103, ");

        return result.toString();
    }
}