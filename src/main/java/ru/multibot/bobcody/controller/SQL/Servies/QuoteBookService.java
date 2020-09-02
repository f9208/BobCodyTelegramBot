package ru.multibot.bobcody.controller.SQL.Servies;

import ru.multibot.bobcody.controller.SQL.Entities.QuoteBook;

public interface QuoteBookService {
    public void add(QuoteBook quoteBook);
    public void deleteById(Long id);
    public QuoteBook getById(Long id);
}
