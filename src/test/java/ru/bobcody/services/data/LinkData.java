package ru.bobcody.services.data;

import ru.bobcody.entities.Link;

import java.time.LocalDateTime;

import static ru.bobcody.services.data.GuestsData.DMITRY;

public class LinkData {
    public static Link LINK_1;
    public static LocalDateTime DATE_1 = LocalDateTime.of(2020, 11, 12, 04, 24, 33);


    static {
        LINK_1 = new Link("c:/aaaa/local",
                "some_name_for_image.jpg",
                239494L,
                DMITRY,
                ChatData.GROUP_CHAT, DATE_1);
    }
}
