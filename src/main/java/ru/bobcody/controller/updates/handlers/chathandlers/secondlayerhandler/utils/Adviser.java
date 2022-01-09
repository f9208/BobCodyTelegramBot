package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bobcody.thirdpartyapi.fuckinggreatadvice.FuckingGreatAdviser;

import static ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.SERVICE_FGA_NOT_AVAILABLE;

@Slf4j
@Component
@RequiredArgsConstructor
public class Adviser {
    private final FuckingGreatAdviser fuckingGreatAdviser;

    public String getAdvice() {
        log.info("make advice");
        String result;
        try {
            result = fuckingGreatAdviser.getAdvice();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("service is not available");
            result = SERVICE_FGA_NOT_AVAILABLE;
        }
        return result;
    }
}
