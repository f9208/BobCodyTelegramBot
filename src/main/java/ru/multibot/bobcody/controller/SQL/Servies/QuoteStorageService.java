package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.stereotype.Component;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteInsideStorage;

@Component
public interface QuoteStorageService {
    public void add(QuoteInsideStorage quoteInsideStorage);

    public void deleteById(Long id);

    public QuoteInsideStorage getSingleQuoteFromStorageById(Long id);

    Long adderQuote(Long a);

    public Long getMaxID();

    public boolean existById(long id);

    public boolean existByDate(long date);

    public Long getAuthorByDateAdded(Long dateAdded);

    public int getSizeDB();
}
