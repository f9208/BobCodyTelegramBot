package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteBook;

@Repository
public interface QuoteBookRepository extends CrudRepository<QuoteBook, Long> {
    public QuoteBook findQuoteBookByData(Integer date);

    @Query(value = "SELECT count(*) from public.quotation_book", nativeQuery = true)
    int getSizeDB();

    @Query(value = "select quotation_text from public.quotation_book! where quotation_id=:id", nativeQuery = true)
    String iii(@Param("id") int id);
}