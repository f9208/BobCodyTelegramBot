package ru.bobcody.controller;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TimeTest {
    @Test
    void getTime() {
        TimeZone calliforniaTimeZone = TimeZone.getTimeZone("America/Los_Angeles");

        TimeZone bratislava = TimeZone.getTimeZone("Europe/Bratislava");
        TimeZone y = TimeZone.getTimeZone("");
        System.out.println(y);
        LocalDateTime ddd = LocalDateTime.now();
        System.out.println(ddd.atZone(bratislava.toZoneId()));
        System.out.println(LocalDateTime.now(calliforniaTimeZone.toZoneId()).format(DateTimeFormatter.ofPattern("HH:mm")));
        System.out.println(LocalDateTime.now(bratislava.toZoneId()).format(DateTimeFormatter.ofPattern("HH:mm")));

    }
}
