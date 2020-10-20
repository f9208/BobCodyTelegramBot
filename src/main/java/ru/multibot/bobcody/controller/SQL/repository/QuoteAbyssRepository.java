package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;

@Repository
public interface QuoteAbyssRepository extends CrudRepository<Quote, Long> {
//    @Query(value = "SELECT * From public.quote_for_approval", nativeQuery = true)
//    boolean add(int dateAdded,  String text, long author_id);

    @Query(value = "select quote_id from public.quotation_abyss where quotation_abyss.date_added=:date", nativeQuery = true)
    Long getQuoteIdByDate(@Param("date") long date);


    //велосипед, там, скорее всего, уже есть подобное
    @Query(value = "SELECT EXISTS (SELECT quote_id FROM public.quotation_abyss where quote_id=:checkId)",
            nativeQuery= true)
    boolean existsQuoteInsideAbysse(@Param("checkId") Long l);

    @Query (value = "SELECT date_added FROM public.quotation_abyss WHERE quote_id=:byId",
            nativeQuery = true)
    Long getDateAddedByQuoteId(@Param("byId") Long timeAdded);

    @Query (value = "SELECT author_id FROM public.quotation_abyss WHERE quote_id=:byId",
            nativeQuery = true)
    Long getAuthorIdByQuoteId(@Param("byId") Long timeAdded);

}
