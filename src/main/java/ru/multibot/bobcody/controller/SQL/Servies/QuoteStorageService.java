package ru.multibot.bobcody.controller.SQL.Servies;

import ru.multibot.bobcody.controller.SQL.Entities.QuoteStorage;

public interface QuoteStorageService {
    public void add(QuoteStorage quoteStorage);
    public void deleteById(Long id);
    public QuoteStorage getById(Long id);
}
