package ru.bobcody.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.data.entities.Chat;

@Repository
public interface IChatRepository extends CrudRepository<Chat, Long> {
}
