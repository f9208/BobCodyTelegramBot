package ru.bobcody.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bobcody.Entities.QuoteEntityAbyss;

@Repository
public interface QuoteAbyssRepository extends CrudRepository<QuoteEntityAbyss, Long> {

    @Modifying
    @Query(value = "INSERT INTO public.quotation_storage(author_id, date_added, date_approved, quote_text) " +
            "SELECT quotation_abyss.guest_id," +
            " quotation_abyss.date_added, " +
            ":currentTime, " +
            " quotation_abyss.quote_text FROM public.quotation_abyss" +
            " where quotation_abyss.id=:quote_id", nativeQuery = true)
    void approveQuote(@Param("quote_id") long id, @Param("currentTime") long unixTime);

    @Modifying
    @Query(value = "INSERT INTO public.caps_quotation_storage(author, date_added, date_approved, caps_text) " +
            "SELECT quotation_abyss.guest_id," +
            " quotation_abyss.date_added, " +
            ":currentTime, " +
            " quotation_abyss.quote_text FROM public.quotation_abyss" +
            " where quotation_abyss.id=:quote_id", nativeQuery = true)
    void approveCaps(@Param("quote_id") long id, @Param("currentTime") long unixTime);


    @Query(value = "SELECT id FROM public.quotation_abyss WHERE quotation_abyss.date_added=:date", nativeQuery = true)
    Long getQuoteIdByDate(@Param("date") long date);

    @Query(value = "SELECT date_added FROM public.quotation_abyss WHERE id=:byId",
            nativeQuery = true)
    Long getDateAddedById(@Param("byId") Long timeAdded);

    @Query(value = "SELECT id FROM public.quotation_abyss WHERE id=:byId",
            nativeQuery = true)
    Long getAuthorIdByQuoteId(@Param("byId") Long timeAdded);

    boolean existsQuoteById(Long id);

    QuoteEntityAbyss getQuoteById(Long id);

}
