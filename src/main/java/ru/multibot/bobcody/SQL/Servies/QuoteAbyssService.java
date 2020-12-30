package ru.multibot.bobcody.SQL.Servies;

import org.springframework.stereotype.Component;
import ru.multibot.bobcody.SQL.Entities.Quote;

@Component
public interface QuoteAbyssService {

    public void add(Quote quote);

    public void delete(Long id);

    public Long getQuoteIdByDateAdded(Long date);

    public boolean containtInAbyss(Long id);

    public Long getDateAddedByQuoteId(Long quoteId);

    public Long getAuthorIdByQuoteId(Long quoteId);
}
