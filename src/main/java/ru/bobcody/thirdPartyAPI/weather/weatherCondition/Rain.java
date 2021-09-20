package ru.bobcody.thirdPartyAPI.weather.weatherCondition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Rain {
    @JsonProperty("3h")
    double mm;

    @Override
    public String toString() {

        String result = "" + mm + " мм воды за 3 часа";
        return result;
    }
}
