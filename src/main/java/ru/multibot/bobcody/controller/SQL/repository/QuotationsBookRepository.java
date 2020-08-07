package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.QuotationsBook;

@Repository
public interface QuotationsBookRepository extends CrudRepository<QuotationsBook, Long>{
    public QuotationsBook findQuotationsBookByData(Integer date);
}
