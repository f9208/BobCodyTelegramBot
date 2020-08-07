package ru.multibot.bobcody.controller.SQL.Servies;


import ru.multibot.bobcody.controller.SQL.Entities.Guest;

public interface GuestService {
    void add(Guest guest);

    Guest deleteById(long id);

//    Guest findById(long id);

    boolean comprise(long id);

}
