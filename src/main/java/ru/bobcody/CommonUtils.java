package ru.bobcody;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.bobcody.domain.Chat;
import ru.bobcody.domain.Guest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static Guest convertUserToGuest(org.telegram.telegrambots.meta.api.objects.User user) {
        Guest guest = new Guest();

        guest.setId(user.getId());
        guest.setFirstName(user.getFirstName());
        guest.setLastName(user.getLastName());
        guest.setUserName(user.getUserName());
        guest.setLanguageCode(user.getLanguageCode());

        return guest;
    }

    public static Chat convertTgChat(org.telegram.telegrambots.meta.api.objects.Chat tgChat) {

        Chat chat = new Chat();

        chat.setId(tgChat.getId());
        chat.setType(tgChat.getType());
        chat.setFirstName(tgChat.getFirstName());
        chat.setLastName(tgChat.getLastName());
        chat.setUserName(tgChat.getUserName());

        return chat;
    }

    public static LocalDateTime epochToLocalDateTime(long milliSecond) {
        return LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .format(new Date(milliSecond * 1000)));
    }

    public static void checkEmpty(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException(message);
        }
    }
}

