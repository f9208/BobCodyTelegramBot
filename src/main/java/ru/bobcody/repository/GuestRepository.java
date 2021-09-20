package ru.bobcody.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.Guest;

import java.util.List;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long> {

    List<Guest> findAllByOrderById();

    List<Guest> findAllBy();

    Guest findGuestById(Long id);
}