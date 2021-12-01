package ru.bobcody.thirdPartyAPI.weather;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
class MainComponentForecast {
    private int temp;
    @JsonProperty("feels_like")
    private int feelsLike;
    @JsonProperty("temp_min")
    private int tempMin;
    @JsonProperty("temp_max")
    private int tempMax;
    private int pressure;
    @JsonProperty("sea_level")
    private int seaLevel;
    @JsonProperty("grnd_level")
    private int groundLevel;
    private int humidity;
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

//    @Override
//    public String toString() {
//        StringBuilder result = new StringBuilder();
//        result.append("t ").append(temp).append("\u2103, ");
//
//        return result.toString();
//    }
}