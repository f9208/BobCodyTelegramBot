package ru.bobcody.services.data;

import ru.bobcody.data.entities.Quote;
import ru.bobcody.data.entities.Type;

import java.time.LocalDateTime;

import static ru.bobcody.services.data.GuestsData.DMITRY;

public class QuoteDate {
    public static final long QUOTE_ID_1 = 1;
    public static final long QUOTE_ID_2 = 2;
    public static final long QUOTE_ID_3 = 3;
    public static final long QUOTE_ID_4 = 4;

    public static final Quote QUOTE_0_NEW =
            new Quote("добавленная только из тестов",
                    LocalDateTime.now(),
                    Type.ABYSS, DMITRY);

    public static final Quote QUOTE_1_ABYSS =
            new Quote(QUOTE_ID_1, "цитата 1 в бездну",
                    LocalDateTime.parse("2021-11-27T15:01:47"),
                    Type.ABYSS, DMITRY);

    public static final Quote QUOTE_1_APPROVED =
            new Quote(QUOTE_ID_1, "цитата 1 в бездну",
                    LocalDateTime.parse("2021-11-27T15:01:47"),
                    null,
                    Type.CAPS, DMITRY, true, 2, 0);

    public static final Quote QUOTE_2_ABYSS =
            new Quote(QUOTE_ID_2, "обычная цитата 2",
                    LocalDateTime.parse("2021-11-27T15:01:43"),
                    Type.ABYSS, DMITRY);

    public static final Quote QUOTE_2_APPROVED =
            new Quote(QUOTE_ID_2, "обычная цитата 2",
                    LocalDateTime.parse("2021-11-27T15:01:43"),
                    LocalDateTime.parse("2021-11-27T17:01:12"),
                    Type.REGULAR, DMITRY, true, 0, 2);

    public static final Quote QUOTE_3_APPROVED =
            new Quote(QUOTE_ID_3, "утвержденная обычная цитата",
                    LocalDateTime.parse("2021-11-27T15:01:17"),
                    LocalDateTime.parse("2020-11-27T15:01:37"),
                    Type.REGULAR, DMITRY, true, 0, 1);

    public static final Quote QUOTE_4_APPROVED =
            new Quote(QUOTE_ID_4, "утвержденный капс",
                    LocalDateTime.parse("2021-11-17T03:01:47"),
                    LocalDateTime.parse("2022-11-27T15:01:47"),
                    Type.CAPS, DMITRY, true, 1, 0);

}
