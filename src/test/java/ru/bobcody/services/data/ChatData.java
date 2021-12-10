package ru.bobcody.services.data;

import ru.bobcody.entities.Chat;

import static ru.bobcody.services.data.TelegramChat.*;

public class ChatData {
    public static final Chat PRIVAT_CHAT = new Chat(PRIVATE_TELEGRAM_CHAT);
    public static final Chat GROUP_CHAT = new Chat(GROUP_TELEGRAM_CHAT);
    public static final Chat UNSAVED_CHAT = new Chat(CHANNEL_TELEGRAM_CHAT);
}
