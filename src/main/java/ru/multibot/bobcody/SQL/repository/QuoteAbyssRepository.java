package ru.multibot.bobcody.SQL.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.SQL.Entities.Quote;

@Repository
public interface QuoteAbyssRepository extends CrudRepository<Quote, Long> {

    @Query(value = "SELECT quote_id FROM public.quotation_abyss WHERE quotation_abyss.date_added=:date", nativeQuery = true)
    Long getQuoteIdByDate(@Param("date") long date);

    @Query(value = "SELECT date_added FROM public.quotation_abyss WHERE quote_id=:byId",
            nativeQuery = true)
    Long getDateAddedByQuoteId(@Param("byId") Long timeAdded);

    @Query(value = "SELECT author_id FROM public.quotation_abyss WHERE quote_id=:byId",
            nativeQuery = true)
    Long getAuthorIdByQuoteId(@Param("byId") Long timeAdded);

    boolean existsQuoteByQuoteId(Long id);

    Quote getQuoteByQuoteId(Long id);

}
