package ru.bobcody.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bobcody.domain.Link;

import java.time.LocalDateTime;

public interface LinkRepository extends JpaRepository<Link, Integer> {
    Link findLinkByName(String name);

    @Modifying
    @Query(value = "INSERT INTO public.links(date, enabled, path, size, chat_id, guest_id, name) " +
            "VALUES (:date, :enabled, :path, :size, :chat_id, :guest_id, :name)", nativeQuery = true)
    int saveOne(
            @Param("date") LocalDateTime localDateTime,
            @Param("enabled") boolean enabled,
            @Param("path") String path,
            @Param("size") long size,
            @Param("chat_id") long chatId,
            @Param("guest_id") long guestId,
            @Param("name") String name);
}