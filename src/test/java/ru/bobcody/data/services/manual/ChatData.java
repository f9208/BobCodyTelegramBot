package ru.bobcody.data.services.manual;

import ru.bobcody.domain.Chat;

import static ru.bobcody.data.services.manual.TelegramChat.*;

public class ChatData {
    public static final Chat PRIVAT_CHAT = new Chat(PRIVATE_TELEGRAM_CHAT);
    public static final Chat GROUP_CHAT = new Chat(GROUP_TELEGRAM_CHAT);
    public static final Chat UNSAVED_CHAT = new Chat(CHANNEL_TELEGRAM_CHAT);
}
