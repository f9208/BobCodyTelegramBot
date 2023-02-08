package ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.bobcody.thirdpartyapi.fuckinggreatadvice.FuckingGreatAdviser;

@Slf4j
//@Component
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
            result = TextConstantHandler.SERVICE_FGA_NOT_AVAILABLE;
        }
        return result;
    }
}
