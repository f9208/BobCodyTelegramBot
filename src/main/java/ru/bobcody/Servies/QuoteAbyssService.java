package ru.bobcody.Servies;

import ru.bobcody.Entities.QuoteEntityAbyss;

public interface QuoteAbyssService {

    void add(QuoteEntityAbyss quoteEntityAbyss);

    Long getQuoteIdByDateAdded(Long date);

    boolean containInAbyss(Long id);

    Long approveQuote(Long id);

    Long approveCaps(Long id);
}
