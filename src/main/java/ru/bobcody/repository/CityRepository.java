package ru.bobcody.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bobcody.domain.City;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameIgnoreCase(String name);
}