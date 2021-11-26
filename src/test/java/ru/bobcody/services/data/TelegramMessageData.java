package ru.bobcody.services.data;

import org.telegram.telegrambots.meta.api.objects.Message;

import static ru.bobcody.services.data.TelegramChat.GROUP_TELEGRAM_CHAT;
import static ru.bobcody.services.data.TelegramChat.PRIVATE_TELEGRAM_CHAT;
import static ru.bobcody.services.data.TelegramUser.DMITRY_TELEGRAM;
import static ru.bobcody.services.data.TelegramUser.SERGY_TELEGRAM;

public class TelegramMessageData {
    public static final Integer TELEGRAM_MESSAGE_1_ID = 33331;
    public static final Integer TELEGRAM_MESSAGE_2_ID = 33332;
    public static final Integer TELEGRAM_MESSAGE_3_ID = 33333;
    public static final Integer TELEGRAM_MESSAGE_4_ID = 33334;
    public static final int TELEGRAM_MESSAGE_1_DATE = 1637353964; //time equal 2021-11-19T23:32:44 utc+3
    public static final int TELEGRAM_MESSAGE_2_DATE = 1637435530; //time  equal 2021-11-20T22:12:10 utc+3
    public static final int TELEGRAM_MESSAGE_3_DATE = 1637526152; //time  equal 2021-11-21T23:22:32 utc+3
    public static final int TELEGRAM_MESSAGE_4_DATE = 1637572330; //time  equal 2021-11-22T12:12:10 utc+3


    public static final Message TELEGRAM_MESSAGE_1 = new Message();

    static {
        TELEGRAM_MESSAGE_1.setMessageId(TELEGRAM_MESSAGE_1_ID);
        TELEGRAM_MESSAGE_1.setText("some text");
        TELEGRAM_MESSAGE_1.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_1.setFrom(DMITRY_TELEGRAM);
        TELEGRAM_MESSAGE_1.setDate(TELEGRAM_MESSAGE_1_DATE);
    }

    public static final Message TELEGRAM_MESSAGE_2 = new Message();

    static {
        TELEGRAM_MESSAGE_2.setMessageId(TELEGRAM_MESSAGE_2_ID);
        TELEGRAM_MESSAGE_2.setText("another text message");
        TELEGRAM_MESSAGE_2.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_2.setFrom(SERGY_TELEGRAM);
        TELEGRAM_MESSAGE_2.setDate(TELEGRAM_MESSAGE_2_DATE); //time  equal 2021-11-20T22:12:10 utc+3
    }

    public static final Message TELEGRAM_MESSAGE_3 = new Message();

    static {
        TELEGRAM_MESSAGE_3.setMessageId(TELEGRAM_MESSAGE_3_ID);
        TELEGRAM_MESSAGE_3.setText("private message");
        TELEGRAM_MESSAGE_3.setChat(PRIVATE_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_3.setFrom(SERGY_TELEGRAM);
        TELEGRAM_MESSAGE_3.setDate(TELEGRAM_MESSAGE_3_DATE); //time  equal 2021-11-21T23:22:32 utc+3
    }

    public static final Message TELEGRAM_MESSAGE_4 = new Message();

    static {
        TELEGRAM_MESSAGE_4.setMessageId(TELEGRAM_MESSAGE_4_ID);
        TELEGRAM_MESSAGE_4.setText("next text message");
        TELEGRAM_MESSAGE_4.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_4.setFrom(SERGY_TELEGRAM);
        TELEGRAM_MESSAGE_4.setDate(TELEGRAM_MESSAGE_4_DATE); //time  equal 2021-11-22T12:12:10 utc+3
    }
}
