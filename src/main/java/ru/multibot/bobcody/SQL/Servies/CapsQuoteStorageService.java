package ru.multibot.bobcody.SQL.Servies;

import ru.multibot.bobcody.SQL.Entities.CapsQuoteEntityStorage;


public interface CapsQuoteStorageService {

    boolean existById(Long id);

    CapsQuoteEntityStorage getById(Long id);

    boolean containInCapsQuoteStorage(Long id);

    Long getMaxID();

    void handleAdd(Long key, String value);
}
