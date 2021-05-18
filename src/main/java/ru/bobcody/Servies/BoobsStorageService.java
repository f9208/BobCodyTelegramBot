package ru.bobcody.Servies;

import ru.bobcody.Entities.BoobsStorage;

public interface BoobsStorageService {
    void add(BoobsStorage boobsStorage);
    String getById (Long id);
    Long getSizeDB();
    Iterable<BoobsStorage> getAllAsIterator();

}
