package ru.multibot.bobcody.SQL.Servies;


import ru.multibot.bobcody.SQL.Entities.Guest;

public interface GuestService {
    void add(Guest guest);

    boolean comprise(long id);
}
