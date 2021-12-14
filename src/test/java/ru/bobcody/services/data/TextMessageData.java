package ru.bobcody.services.data;

import ru.bobcody.data.entities.TextMessage;

import static ru.bobcody.services.data.GuestsData.DMITRY;
import static ru.bobcody.services.data.GuestsData.SERGY;
import static ru.bobcody.services.data.TelegramMessageData.*;

public class TextMessageData {
    public final static long TEXT_MESSAGE_1_ID = 1L;
    public final static long TEXT_MESSAGE_2_ID = 2L;
    public final static long TEXT_MESSAGE_3_ID = 3L;
    public final static long TEXT_MESSAGE_4_ID = 4L;
    public final static TextMessage TEXT_MESSAGE_1 = new TextMessage(TELEGRAM_MESSAGE_1);
    public final static TextMessage TEXT_MESSAGE_2 = new TextMessage(TELEGRAM_MESSAGE_2);
    public final static TextMessage TEXT_MESSAGE_3 = new TextMessage(TELEGRAM_MESSAGE_3);
    public final static TextMessage TEXT_MESSAGE_4 = new TextMessage(TELEGRAM_MESSAGE_4);
    public final static TextMessage TEXT_MESSAGE_UNSAVED = new TextMessage(TELEGRAM_MESSAGE_5);

    static {
        TEXT_MESSAGE_1.setId(TEXT_MESSAGE_1_ID);
        TEXT_MESSAGE_1.setGuest(DMITRY);
        TEXT_MESSAGE_2.setId(TEXT_MESSAGE_2_ID);
        TEXT_MESSAGE_2.setGuest(SERGY);
        TEXT_MESSAGE_3.setId(TEXT_MESSAGE_3_ID);
        TEXT_MESSAGE_3.setGuest(SERGY);
        TEXT_MESSAGE_4.setId(TEXT_MESSAGE_4_ID);
        TEXT_MESSAGE_4.setGuest(SERGY);
    }
}
