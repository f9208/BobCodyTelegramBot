package ru.multibot.bobcody.SQL.Servies;

import ru.multibot.bobcody.SQL.Entities.QuoteEntityAbyss;

public interface QuoteAbyssService {

    void add(QuoteEntityAbyss quoteEntityAbyss);

    Long getQuoteIdByDateAdded(Long date);

    boolean containInAbyss(Long id);

    Long approveQuote(Long id);

    Long approveCaps(Long id);
}
