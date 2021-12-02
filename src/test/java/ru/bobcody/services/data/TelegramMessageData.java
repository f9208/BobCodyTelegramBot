package ru.bobcody.services.data;

import org.telegram.telegrambots.meta.api.objects.Message;

import static ru.bobcody.services.data.TelegramChat.GROUP_TELEGRAM_CHAT;
import static ru.bobcody.services.data.TelegramChat.PRIVATE_TELEGRAM_CHAT;
import static ru.bobcody.services.data.TelegramUser.*;

public class TelegramMessageData {
    public static final Integer TELEGRAM_MESSAGE_1_ID = 1;
    public static final Integer TELEGRAM_MESSAGE_2_ID = 2;
    public static final Integer TELEGRAM_MESSAGE_3_ID = 3;
    public static final Integer TELEGRAM_MESSAGE_4_ID = 4;
    public static final Integer TELEGRAM_MESSAGE_5_ID = 5;
    public static final Integer TELEGRAM_MESSAGE_6_ID = 6;
    public static final int TELEGRAM_MESSAGE_1_DATE = 1637353964; //time equal 2021-11-19T23:32:44 utc+3
    public static final int TELEGRAM_MESSAGE_2_DATE = 1637435530; //time  equal 2021-11-20T22:12:10 utc+3
    public static final int TELEGRAM_MESSAGE_3_DATE = 1637526152; //time  equal 2021-11-21T23:22:32 utc+3
    public static final int TELEGRAM_MESSAGE_4_DATE = 1637572330; //time  equal 2021-11-22T12:12:10 utc+3
    public static final int TELEGRAM_MESSAGE_5_DATE = 1637572330; //time  equal 2021-11-22T12:12:10 utc+3
    public static final int TELEGRAM_MESSAGE_6_DATE = 1637572330; //time  equal 2021-11-22T12:12:10 utc+3

    public static final Message TELEGRAM_MESSAGE_1 = new Message();
    public static final Message TELEGRAM_MESSAGE_2 = new Message();
    public static final Message TELEGRAM_MESSAGE_3 = new Message();
    public static final Message TELEGRAM_MESSAGE_4 = new Message();
    public static final Message TELEGRAM_MESSAGE_5 = new Message();
    public static final Message TELEGRAM_MESSAGE_6 = new Message();

    static {
        init();
    }

    public static void init() {
        TELEGRAM_MESSAGE_1.setMessageId(TELEGRAM_MESSAGE_1_ID);
        TELEGRAM_MESSAGE_1.setText("some text");
        TELEGRAM_MESSAGE_1.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_1.setFrom(DMITRY_TELEGRAM);
        TELEGRAM_MESSAGE_1.setDate(TELEGRAM_MESSAGE_1_DATE);

        TELEGRAM_MESSAGE_2.setMessageId(TELEGRAM_MESSAGE_2_ID);
        TELEGRAM_MESSAGE_2.setText("another text message");
        TELEGRAM_MESSAGE_2.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_2.setFrom(SERGY_TELEGRAM);
        TELEGRAM_MESSAGE_2.setDate(TELEGRAM_MESSAGE_2_DATE); //time  equal 2021-11-20T22:12:10 utc+3

        TELEGRAM_MESSAGE_3.setMessageId(TELEGRAM_MESSAGE_3_ID);
        TELEGRAM_MESSAGE_3.setText("private message");
        TELEGRAM_MESSAGE_3.setChat(PRIVATE_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_3.setFrom(SERGY_TELEGRAM);
        TELEGRAM_MESSAGE_3.setDate(TELEGRAM_MESSAGE_3_DATE); //time  equal 2021-11-21T23:22:32 utc+3

        TELEGRAM_MESSAGE_4.setMessageId(TELEGRAM_MESSAGE_4_ID);
        TELEGRAM_MESSAGE_4.setText("next text message");
        TELEGRAM_MESSAGE_4.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_4.setFrom(SERGY_TELEGRAM);
        TELEGRAM_MESSAGE_4.setDate(TELEGRAM_MESSAGE_4_DATE); //time  equal 2021-11-22T12:12:10 utc+3

        TELEGRAM_MESSAGE_5.setMessageId(TELEGRAM_MESSAGE_5_ID);
        TELEGRAM_MESSAGE_5.setText("Save this text");
        TELEGRAM_MESSAGE_5.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_5.setFrom(DMITRY_TELEGRAM);
        TELEGRAM_MESSAGE_5.setDate(TELEGRAM_MESSAGE_5_DATE); //time  equal 2021-11-22T12:12:10 utc+3TELEGRAM_MESSAGE_5.setMessageId(TELEGRAM_MESSAGE_5_ID);

        TELEGRAM_MESSAGE_6.setMessageId(TELEGRAM_MESSAGE_6_ID);
        TELEGRAM_MESSAGE_6.setText("admin message");
        TELEGRAM_MESSAGE_6.setChat(GROUP_TELEGRAM_CHAT);
        TELEGRAM_MESSAGE_6.setFrom(ADMIN_TELEGRAM);
        TELEGRAM_MESSAGE_6.setDate(TELEGRAM_MESSAGE_6_DATE); //time  equal 2021-11-22T12:12:10 utc+3
    }
}
