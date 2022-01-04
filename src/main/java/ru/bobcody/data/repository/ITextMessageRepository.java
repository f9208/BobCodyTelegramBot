package ru.bobcody.data.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bobcody.data.entities.TextMessage;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ITextMessageRepository extends CrudRepository<TextMessage, Long> {
    List<TextMessage> findAllByDateTimeBetweenAndChatIdOrderByDateTime(LocalDateTime start, LocalDateTime end, long chatId);

    @Query(value = "SELECT DISTINCT DATE(date_time) FROM text_message " +
            "WHERE chat=:chatId " +
            "ORDER BY DATE(date_time) DESC",
            nativeQuery = true)
    List<Date> findAllDateTime(@Param("chatId") long chatId);

    @Modifying
    @Query(value = "INSERT INTO " +
            "public.text_message(date_time, telegram_id, text_message, chat, guest) " +
            "VALUES (:dateTime, :telegramId, :textMessage, :chat, :guestId)",
            nativeQuery = true)
    Integer saveOne(@Param("dateTime") LocalDateTime localDateTime,
                    @Param("telegramId") long telegramId,
                    @Param("textMessage") String textMessage,
                    @Param("chat") long chatId,
                    @Param("guestId") long guestId);

    @Query(value = "SELECT guests.first_name, COUNT(text_message.text_message) AS total " +
            "FROM text_message " +
            "INNER JOIN  guests ON text_message.guest = guests.id " +
            "WHERE text_message.chat=:chatId " +
            "AND text_message.date_time>=:start " +
            "AND text_message.date_time<:end " +
            "GROUP BY guests.first_name " +
            "ORDER BY total DESC " +
            "LIMIT 5", nativeQuery = true)
    List<String> getTop(@Param("chatId") long chatId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);
}