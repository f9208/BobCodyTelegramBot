package ru.bobcody.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.Guest;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long> {

    List<Guest> findAllBy();

    Optional<Guest> findById(Long id);
}
