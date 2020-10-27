package ru.multibot.bobcody.controller.SQL.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;

import java.util.List;
import java.util.Set;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long>{

    List<Guest> findAllByOrderByUserID();
}
