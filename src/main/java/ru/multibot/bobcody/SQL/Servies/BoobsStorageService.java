package ru.multibot.bobcody.SQL.Servies;

import ru.multibot.bobcody.SQL.Entities.BoobsStorage;

public interface BoobsStorageService {
    void add(BoobsStorage boobsStorage);
    String getById (Long id);
    Long getSizeDB();
    Iterable<BoobsStorage> getAllAsIterator();

}
