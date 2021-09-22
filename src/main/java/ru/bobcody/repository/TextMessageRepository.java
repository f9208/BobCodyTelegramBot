package ru.bobcody.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.TextMessage;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TextMessageRepository extends CrudRepository<TextMessage, Long> {
    List<TextMessage> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT DISTINCT DATE(date_time) from text_message order by date(date_time) DESC", nativeQuery = true)
    List<Date> findAllDateTime();
}
