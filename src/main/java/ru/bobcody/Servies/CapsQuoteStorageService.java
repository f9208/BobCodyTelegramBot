package ru.bobcody.Servies;

import ru.bobcody.Entities.CapsQuoteEntityStorage;


public interface CapsQuoteStorageService {

    boolean existById(Long id);

    CapsQuoteEntityStorage getById(Long id);

    boolean containInCapsQuoteStorage(Long id);

    Long getMaxID();

    void handleAdd(Long key, String value);
}
