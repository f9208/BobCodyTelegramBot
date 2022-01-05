package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bobcody.thirdpartyapi.fuckinggreatadvice.FuckingGreatAdviser;

import static ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.SERVICE_FGA_NOT_AVAILABLE;

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
            result = SERVICE_FGA_NOT_AVAILABLE;
        }
        return result;
    }
}
