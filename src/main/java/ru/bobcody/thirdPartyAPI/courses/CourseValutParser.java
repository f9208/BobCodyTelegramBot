package ru.bobcody.thirdPartyAPI.courses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class CourseValutParser {
    private static String link = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static URL cbrCource;
    public String date;

    private ValCurs getValCursByXml() {
        Unmarshaller um;
        JAXBContext context;
        ValCurs result = null;

        try {
            cbrCource = new URL(link);
            context = JAXBContext.newInstance(ValCurs.class);
            um = context.createUnmarshaller();
            //не нравится, переделать как то на кэширование.
            result = (ValCurs) um.unmarshal(new InputStreamReader(cbrCource.openStream()));
            cbrCource = null;
            date = result.getDate();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private Map<String, Valute> getMapByKeyAsValuteCode() {
        Map<String, Valute> result = new HashMap<>();
        for (Valute n : getValCursByXml().getValutes()) {
            result.put(n.getCharCode(), n);
        }
        return result;
    }

    public Valute getValuteByCharCode(String charCode) {
        return getMapByKeyAsValuteCode().get(charCode);
    }
}
