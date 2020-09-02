package ru.multibot.bobcody.controller.SQL.Servies;

import ru.multibot.bobcody.controller.SQL.Entities.Quote;

public interface QuoteService {

    public void add(Quote quote);
    public void delete(Long id);

}
