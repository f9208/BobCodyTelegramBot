package ru.multibot.bobcody.SQL.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.SQL.Entities.BoobsStorage;

@Repository
public interface BoobsStorageRepository extends CrudRepository<BoobsStorage, Long> {
    @Query(value = "SELECT count(*) from public.boobs_storage", nativeQuery = true)
    int getSizeDB();

    long count();

    Iterable<BoobsStorage> findAll();

    BoobsStorage getBoobsStorageByLink(String link);
}
