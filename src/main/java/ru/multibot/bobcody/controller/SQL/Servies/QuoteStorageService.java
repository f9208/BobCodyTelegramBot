package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.stereotype.Component;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteInsideStorage;

@Component
public interface QuoteStorageService {
     void add(QuoteInsideStorage quoteInsideStorage);

     void deleteById(Long id);

     QuoteInsideStorage getSingleQuoteFromStorageById(Long id);

    Long adderQuote(Long a);

     Long getMaxID();

     boolean existById(long id);

     boolean existByDate(long date);

     Long getAuthorByDateAdded(Long dateAdded);

     int getSizeDB();
}
