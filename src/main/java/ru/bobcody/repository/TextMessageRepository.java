package ru.bobcody.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.TextMessage;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TextMessageRepository extends CrudRepository<TextMessage, Long> {
    List<TextMessage> findAllByDateTimeBetweenAndChatId(LocalDateTime start, LocalDateTime end, long chatId);

    @Query(value = "SELECT DISTINCT DATE(date_time) from text_message where chat=:chatId order by date(date_time) DESC", nativeQuery = true)
    List<Date> findAllDateTime(@Param("chatId")long chatId);
}
