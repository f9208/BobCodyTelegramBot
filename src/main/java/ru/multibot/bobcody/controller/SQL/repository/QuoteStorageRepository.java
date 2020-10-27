package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteInsideStorage;

@Repository
public interface QuoteStorageRepository extends CrudRepository<QuoteInsideStorage, Long> {

    @Query(value = "SELECT count(*) FROM public.quotation_storage", nativeQuery = true)
    int getSizeDB();

    @Query(value = "SELECT quotation_text FROM public.quotation_storage WHERE id=:quote_id", nativeQuery = true)
    String getTextQuoteById(@Param("quote_id") int id);

    @Modifying
    @Query(value = "INSERT INTO public.quotation_storage(author_id, date_added, date_approved, quote_text) " +
            "SELECT quotation_abyss.author_id," +
            " quotation_abyss.date_added, " +
            ":currentTime, " +
            " quotation_abyss.quote_text FROM public.quotation_abyss" +
            " where quotation_abyss.quote_id=:quote_id", nativeQuery = true)
    void approveQuote(@Param("quote_id") long id, @Param("currentTime") long unixTime);

    @Query(value = "SELECT MAX (quote_id) from public.quotation_storage",
            nativeQuery = true)
    Long getMaxID();

    boolean existsByQuoteId(long id);

    boolean existsQuoteInsideStorageByDateAdded(long date);

    @Query(value = "SELECT author_id FROM public.quotation_storage where date_added=:dateAdded",
            nativeQuery = true)
    Long getAuthorByDateAdded(@Param("dateAdded") Long date);

    QuoteInsideStorage getQuoteInsideStorageByQuoteId(long id);
}
