package ru.bobcody.thirdPartyAPI.courses;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "ValCurs")
public class ValCurs {
    private String date;
    private String name;

    @XmlElement(name = "Valute")
    List<Valuta> valutas = new ArrayList<>();

    @XmlAttribute(name = "Date")
    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
