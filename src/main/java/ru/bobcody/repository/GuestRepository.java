package ru.bobcody.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.Guest;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long> {

    List<Guest> findAllBy();

    Optional<Guest> findById(Long id);

    @Transactional
    @Modifying
    @Query(value = "Update guests set first_name=:firstName, last_name=:lastName, " +
            "user_name=:userName, language_code=:languageCode where id=:id",
            nativeQuery = true)
    int update(@Param("id") long id, @Param("firstName") String firstName,
               @Param("userName") String userName, @Param("lastName") String lastName,
               @Param("languageCode") String languageCode);
}
