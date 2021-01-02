package ru.multibot.bobcody.SQL.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.SQL.Entities.QuoteEntityStorage;

@Repository
public interface QuoteStorageRepository extends CrudRepository<QuoteEntityStorage, Long> {


    @Query(value = "SELECT MAX (quote_id) from public.quotation_storage",
            nativeQuery = true)
    Long getMaxID();

    boolean existsByQuoteId(long id);

    boolean existsQuoteEntityStorageByDateAdded(long date);

    @Query(value = "SELECT author_id FROM public.quotation_storage where date_added=:dateAdded",
            nativeQuery = true)
    Long getAuthorByDateAdded(@Param("dateAdded") Long date);

    QuoteEntityStorage getQuoteEntityStorageByQuoteId(long id);


    // ручное добавленеи цитат
    @Modifying
    @Query(value = "INSERT INTO public.quotation_storage(author_id, date_added, date_approved, quote_text)" +
            "SELECT 445682905," +
            ":currentTime, :currentTime, :textQuote", nativeQuery = true)
    void handAdd(@Param("textQuote") String inputText, @Param("currentTime") Long unixTime);
}
