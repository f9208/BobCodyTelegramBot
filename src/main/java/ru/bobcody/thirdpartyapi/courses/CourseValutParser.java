package ru.bobcody.thirdpartyapi.courses;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
//@Component
public class CourseValutParser {
    @Value("${course.sber.link}")
    private String link;
    @Getter
    private String date;

    private ValCurs getValCursByXml() {
        ValCurs result = new ValCurs();
        try {
            URL cbrCourseUrl = new URL(link);
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller um = context.createUnmarshaller();
            result = (ValCurs) um.unmarshal(new InputStreamReader(cbrCourseUrl.openStream()));
            date = result.getDate();
        } catch (JAXBException | IOException e) {
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
