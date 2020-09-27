package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;

@Repository
public interface QuoteAbyssRepository extends CrudRepository<Quote, Long> {
//    @Query(value = "SELECT * From public.quote_for_approval", nativeQuery = true)
//    boolean add(int data,  String text, long author_id);


}
