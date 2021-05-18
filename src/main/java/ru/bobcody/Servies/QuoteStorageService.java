package ru.bobcody.Servies;

import ru.bobcody.Entities.QuoteEntityStorage;

public interface QuoteStorageService {
    void add(QuoteEntityStorage quoteEntityStorage);

    QuoteEntityStorage getSingleQuoteFromStorageById(Long id);

    Long adderQuote(Long a);

    Long getMaxID();

    boolean existById(long id);

    boolean containInQuoteStorage(Long added);

    // ручное добавление цитаток
    void handleAdd(Long key, String value);


}
