package ru.bobcody.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.Link;

import java.time.LocalDateTime;

@Repository
public interface ILinkRepository extends CrudRepository<Link, Integer> {
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