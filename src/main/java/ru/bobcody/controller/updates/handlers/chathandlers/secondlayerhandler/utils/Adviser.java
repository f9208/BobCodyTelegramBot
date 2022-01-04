package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bobcody.thirdpartyapi.fuckinggreatadvice.FuckingGreatAdviser;

@Slf4j
@Component
public class Adviser {
    @Autowired
    @Setter
    private FuckingGreatAdviser fuckingGreatAdvicer;

    public String getAdvice() {
        log.info("make advice");
        String result;
        try {
            result = fuckingGreatAdvicer.getAdvice();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("service is not available");
            result = "че то сервис не алё. совет от бота - не еби, блять, другим (и себе) мозги!";
        }
        return result;
    }
}
