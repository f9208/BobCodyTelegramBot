package ru.bobcody.services.data;

import ru.bobcody.entities.CapsQuoteStorage;

import static ru.bobcody.services.data.GuestsData.DMITRY;
import static ru.bobcody.services.data.GuestsData.SERGY;

public class CapsQuoteStorageData {
    public static final long CAPS_1_ID = 51111;
    public static final long CAPS_2_ID = 51112;
    public static final long CAPS_3_ID = 51113;
    public static final CapsQuoteStorage CAPS_QUOTE_S_1 = new CapsQuoteStorage();
    public static final CapsQuoteStorage CAPS_QUOTE_S_2 = new CapsQuoteStorage();
    public static final CapsQuoteStorage CAPS_QUOTE_S_3 = new CapsQuoteStorage();

    static {
        CAPS_QUOTE_S_1.setId(CAPS_1_ID);
        CAPS_QUOTE_S_1.setAuthor(DMITRY);
        CAPS_QUOTE_S_1.setDateAdded(123123L);
        CAPS_QUOTE_S_1.setDateApproved(41242135L);
        CAPS_QUOTE_S_1.setCapsQuoteText("В ЧЕМ СИЛА? В НЬЮТОНАХ!");
    }

    static {
        CAPS_QUOTE_S_2.setId(CAPS_2_ID);
        CAPS_QUOTE_S_2.setAuthor(SERGY);
        CAPS_QUOTE_S_2.setDateAdded(1231233L);
        CAPS_QUOTE_S_2.setDateApproved(412442135L);
        CAPS_QUOTE_S_2.setCapsQuoteText("ЫЫЫ");
    }

    static {
        CAPS_QUOTE_S_3.setId(CAPS_3_ID);
        CAPS_QUOTE_S_3.setAuthor(DMITRY);
        CAPS_QUOTE_S_3.setDateAdded(1232123L);
        CAPS_QUOTE_S_3.setDateApproved(41432135L);
        CAPS_QUOTE_S_3.setCapsQuoteText("WWW");
    }
}
