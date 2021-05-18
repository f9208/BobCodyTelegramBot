package ru.bobcody.Servies;


import ru.bobcody.Entities.Guest;

public interface GuestService {
    void add(Guest guest);
    boolean comprise(long id);
}
