package ru.bobcody.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.Chat;

@Repository
public interface IChatRepository extends CrudRepository<Chat, Long> {
}
