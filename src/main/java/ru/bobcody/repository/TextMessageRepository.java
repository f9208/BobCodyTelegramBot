package ru.bobcody.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.TextMessage;

@Repository
public interface TextMessageRepository extends CrudRepository<TextMessage, Long> {
}
