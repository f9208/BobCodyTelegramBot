package ru.multibot.bobcody.controller.SQL.Servies;

import ru.multibot.bobcody.controller.SQL.Entities.QuoteInsideStorage;

public interface QuoteStorageService {
    public void add(QuoteInsideStorage quoteInsideStorage);
    public void deleteById(Long id);
    public QuoteInsideStorage getById(Long id);
}
