package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.BoobsStorage;
@Repository
public interface BoobsStorageRepository extends CrudRepository<BoobsStorage, Long> {

}
