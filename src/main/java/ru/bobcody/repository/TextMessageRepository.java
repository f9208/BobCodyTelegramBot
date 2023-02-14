package ru.bobcody.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bobcody.domain.TextMessage;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TextMessageRepository extends JpaRepository<TextMessage, Long> {

    @Query(value = "SELECT guest.first_name, COUNT(text_message.text_message) AS total " +
            "FROM text_message " +
            "INNER JOIN  guest ON text_message.guest_id = guest.id " +
            "WHERE text_message.chat_id=:chatId " +
            "AND text_message.date_time>= :after " +
            "AND text_message.date_time< :before " +
            "GROUP BY guest.first_name " +
            "ORDER BY total DESC " +
            "LIMIT 5", nativeQuery = true)
    List<String> getTop(@Param("chatId") long chatId,
                        @Param("after") LocalDateTime after,
                        @Param("before") LocalDateTime before);

}
