package ru.bobcody.data.services.manual;

import ru.bobcody.domain.Guest;

import static ru.bobcody.data.services.manual.TelegramUser.*;

public class GuestsData {
    public static final Guest VIKTOR = new Guest(VIKTOR_TELEGRAM);
    public static final Guest DMITRY = new Guest(DMITRY_TELEGRAM);
    public static final Guest SERGY = new Guest(SERGY_TELEGRAM);
    public static final Guest ADMIN = new Guest(ADMIN_TELEGRAM);

    static {
        init();
    }

    public static void init() {
        SERGY.setCityName("Vorkuta");
    }
}