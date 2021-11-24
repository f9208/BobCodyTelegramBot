package ru.bobcody.thirdPartyAPI.courses;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Getter
@Setter
public class CourseValutParser {
    private static String link = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static URL cbrCourse;
    public String date;

    private ValCurs getValCursByXml() {
        Unmarshaller um;
        JAXBContext context;
        ValCurs result = null;

        try {
            cbrCourse = new URL(link);
            context = JAXBContext.newInstance(ValCurs.class);
            um = context.createUnmarshaller();
            result = (ValCurs) um.unmarshal(new InputStreamReader(cbrCourse.openStream()));
            cbrCourse = null;
            date = result.getDate();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String, Valuta> getMapByKeyAsValutaCode() {
        Map<String, Valuta> result = new HashMap<>();
        for (Valuta n : getValCursByXml().getValutas()) {
            result.put(n.getCharCode(), n);
        }
        return result;
    }

    @Cacheable(value = "valuta", key = "#charCode")
    public Valuta getValutaByCharCode(String charCode) {
        log.info("get value valuta with key {}", charCode);
        return getMapByKeyAsValutaCode().get(charCode);
    }
}
