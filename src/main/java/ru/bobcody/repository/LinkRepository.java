package ru.bobcody.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.Link;

@Repository
public interface LinkRepository extends CrudRepository<Link, Integer> {
    Link findLinkByName(String name);
}