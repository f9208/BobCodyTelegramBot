package ru.bobcody.thirdPartyAPI.courses;

import javax.xml.bind.annotation.*;

@XmlType(propOrder = {"numCode", "charCode", "nominal", "name", "value"})
public class Valuta {
    private String ID;
    private Integer numCode;
    private String charCode;
    private Integer nominal;
    private String name;
    private String value;

    public String getId() {
        return ID;
    }

    @XmlAttribute(name = "ID")
    public void setId(String id) {
        this.ID = id;
    }

    @XmlElement(name = "NumCode")
    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public Integer getNumCode() {
        return numCode;
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

    public String getCharCode() {
        return charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Valuta{" +
                "ID='" + ID + '\'' +
                ", numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}