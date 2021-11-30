package ru.bobcody.services.data;

import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramUser {
    public static final User VIKTOR_TELEGRAM = new User();
    static {
        VIKTOR_TELEGRAM.setId(1L);
        VIKTOR_TELEGRAM.setFirstName("Viktor");
        VIKTOR_TELEGRAM.setLastName("Petrov");
        VIKTOR_TELEGRAM.setUserName("vampire");
        VIKTOR_TELEGRAM.setLanguageCode("ru");
    }

    public static final User DMITRY_TELEGRAM = new User();
    static {
        DMITRY_TELEGRAM.setId(2L);
        DMITRY_TELEGRAM.setFirstName("Dmitry");
        DMITRY_TELEGRAM.setLastName("Batikov");
        DMITRY_TELEGRAM.setUserName("bad");
        DMITRY_TELEGRAM.setLanguageCode("ru");
    }

    public static final User SERGY_TELEGRAM = new User();
    static {
        SERGY_TELEGRAM.setId(3L);
        SERGY_TELEGRAM.setFirstName("Sergy");
        SERGY_TELEGRAM.setLastName("Morozov");
        SERGY_TELEGRAM.setUserName("moroz");
        SERGY_TELEGRAM.setLanguageCode("ru");
    }
}
