package ru.bobcody.command;

import lombok.Getter;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCourseCommand extends AbstractCommand {

    @Override
    public String execute() {

        RestTemplate restTemplate = new RestTemplate();
        String urlRateSource = settingService.getSberRateUrl();

        RateToday rate = restTemplate.getForObject(urlRateSource, RateToday.class);

        if (rate == null) {
            return "курс валют не доступен";
        }

        return getRateAsPrettyString(rate);
    }

    private String getRateAsPrettyString(RateToday rate) {
        StringBuilder result = new StringBuilder("текущий курс валют по курсу ЦБ РФ на ");

        double hryvnia = Double.parseDouble(rate.getMapCodeValuta().get("UAH").getValue()) / 10;
        double lira = Double.parseDouble(rate.getMapCodeValuta().get("TRY").getValue()) / 10;

        result.append(rate.getDate() + ":\n")
                .append("бакс СШП: ")
                .append(rate.getMapCodeValuta().get("USD").getValue().substring(0, 5))
                .append("\n")
                .append("евро: ")
                .append(rate.getMapCodeValuta().get("EUR").getValue().substring(0, 5))
                .append("\n")
                .append("грывна: ")
                .append(Math.ceil(hryvnia * 100) / 100)
                .append("\n")
                .append("индейка лир: ")
                .append(Math.ceil(lira * 100) / 100)
                .append("\n")
                .append("юань: ").append(rate.getMapCodeValuta().get("CNY").getValue().substring(0, 5))
                .append("\nнефть не нужна, собирай шишки.");
        return result.toString();
    }


    @Getter
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlRootElement(name = "ValCurs")
    private static class RateToday {
        private String date;
        private String name;

        public Map<String, Valuta> getMapCodeValuta() {
            Map<String, Valuta> result = new HashMap<>();
            for (Valuta n : valutas) {
                result.put(n.getCharCode(), n);
            }
            return result;
        }

        @XmlElement(name = "Valute")
        List<Valuta> valutas = new ArrayList<>();

        @XmlAttribute(name = "Date")
        public void setDate(String date) {
            this.date = date;
        }

        @XmlAttribute(name = "name")
        public void setName(String name) {
            this.name = name;
        }
    }

    @Getter
    @XmlType(propOrder = {"numCode", "charCode", "nominal", "name", "value"})
    private static class Valuta {
        private String id;
        private Integer numCode;
        private String charCode;
        private Integer nominal;
        private String name;
        private String value;

        @XmlAttribute(name = "ID")
        public void setId(String id) {
            this.id = id;
        }

        @XmlElement(name = "NumCode")
        public void setNumCode(Integer numCode) {
            this.numCode = numCode;
        }

        @XmlElement(name = "CharCode")
        public void setCharCode(String charCode) {
            this.charCode = charCode;
        }

        @XmlElement(name = "Nominal")
        public void setNominal(Integer nominal) {
            this.nominal = nominal;
        }

        @XmlElement(name = "Name")
        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name = "Value")
        public void setValue(String value) {
            this.value = value.replace(",", ".");
        }

        @Override
        public String toString() {
            return "Valuta{" +
                    "ID='" + id + '\'' +
                    ", numCode=" + numCode +
                    ", charCode='" + charCode + '\'' +
                    ", nominal=" + nominal +
                    ", name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}