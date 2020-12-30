package ru.multibot.bobcody.SQL.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.SQL.Entities.Guest;

import java.util.List;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long>{

    List<Guest> findAllByOrderByUserID();
}
