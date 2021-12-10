package ru.bobcody.services.data;

import org.telegram.telegrambots.meta.api.objects.Chat;

public class TelegramChat {
    public static final Chat PRIVATE_TELEGRAM_CHAT = new Chat();
    static {
        PRIVATE_TELEGRAM_CHAT.setId(1L);
        PRIVATE_TELEGRAM_CHAT.setLastName(null);
        PRIVATE_TELEGRAM_CHAT.setType("private");
        PRIVATE_TELEGRAM_CHAT.setFirstName("cars");
        PRIVATE_TELEGRAM_CHAT.setUserName("privat_with_someone");
    }

    public static final Chat GROUP_TELEGRAM_CHAT = new Chat();
    static {
        GROUP_TELEGRAM_CHAT.setId(242345L);
        GROUP_TELEGRAM_CHAT.setFirstName("flowers and kitty");
        GROUP_TELEGRAM_CHAT.setType("group");
        GROUP_TELEGRAM_CHAT.setLastName("F and K");
        GROUP_TELEGRAM_CHAT.setUserName("chat for housekeeper");
    }

    public static final Chat CHANNEL_TELEGRAM_CHAT = new Chat();
    static {
        CHANNEL_TELEGRAM_CHAT.setId(3L);
        CHANNEL_TELEGRAM_CHAT.setFirstName("mustache Peskova");
        CHANNEL_TELEGRAM_CHAT.setType("channel");
        CHANNEL_TELEGRAM_CHAT.setLastName(null);
        CHANNEL_TELEGRAM_CHAT.setUserName("news chat");
    }
}
