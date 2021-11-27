package ru.bobcody.services.data;

import ru.bobcody.entities.QuoteAbyss;

import static ru.bobcody.services.data.GuestsData.DMITRY;
import static ru.bobcody.services.data.GuestsData.SERGY;

public class QuoteAbyssServiceData {
    public static final long QUOTE_ABYSS_ID_1 = 61111;
    public static final long QUOTE_ABYSS_ID_2 = 61112;
    public static final long QUOTE_ABYSS_ID_3 = 61113;

    public static final QuoteAbyss QUOTE_ABYSS_1 = new QuoteAbyss();
    public static final QuoteAbyss QUOTE_ABYSS_2 = new QuoteAbyss();
    public static final QuoteAbyss QUOTE_ABYSS_3 = new QuoteAbyss();

    static {
        QUOTE_ABYSS_1.setId(QUOTE_ABYSS_ID_1);
        QUOTE_ABYSS_1.setDate(123123L);
        QUOTE_ABYSS_1.setAuthor(DMITRY);
        QUOTE_ABYSS_1.setText("stupid speech");
    }

    static {
        QUOTE_ABYSS_2.setId(QUOTE_ABYSS_ID_2);
        QUOTE_ABYSS_2.setDate(1231233L);
        QUOTE_ABYSS_2.setAuthor(SERGY);
        QUOTE_ABYSS_2.setText("smart speech");
    }

    static {
        QUOTE_ABYSS_3.setId(QUOTE_ABYSS_ID_3);
        QUOTE_ABYSS_3.setDate(1232123L);
        QUOTE_ABYSS_3.setAuthor(DMITRY);
        QUOTE_ABYSS_3.setText("no speech");
    }
}
