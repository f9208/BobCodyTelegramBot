package ru.bobcody.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.Entities.InputMessage;

@Repository
public interface InputMessageRepository extends CrudRepository<InputMessage, Long> {
}
