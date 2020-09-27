package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteStorage;

@Repository
public interface QuoteStorageRepository extends CrudRepository<QuoteStorage, Long> {
    public QuoteStorage findQuaoteBookByData(Integer date);

    @Query(value = "SELECT count(*) FROM public.quotation_book", nativeQuery = true)
    int getSizeDB();

    @Query(value = "SELECT quotation_text FROM public.quotation_book! WHERE quotation_id=:id", nativeQuery = true)
    String getTextQuoteById(@Param("id") int id);

    @Modifying
    @Query(value = "INSERT INTO public.quotation_book(author, data, quote_text) " +
            "SELECT quote_for_approval.author_id," +
            " quote_for_approval.\"data\", " +
            " quote_for_approval.\"text\" FROM public.quote_for_approval" +
            " where quote_for_approval.id=:id", nativeQuery = true)
    void approveQuote(@Param("id") int id);

    @Query(value = "SELECT MAX (quotation_id) from public.quotation_book",
            nativeQuery = true)
    int getMaxID();

    boolean existsByQuotationId(long id);
}
