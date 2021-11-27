package ru.bobcody.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.Quote;
import ru.bobcody.entities.Type;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {
    @Override
    Optional<Quote> findById(Long id);

    @Transactional
    @Modifying
    @Query(value = "update quotes " +
            " set approved=:date, endorsed=true, type='CAPS', caps_id=next value for PUBLIC.caps_id_seq " +
            " where id=:id", nativeQuery = true)
    int approveCaps(@Param("id") long id, @Param("date") LocalDateTime approveTime);

    @Transactional
    @Modifying
    @Query(value = "update quotes " +
            " set approved=:date, endorsed=true, type='REGULAR', regul_id=next value for PUBLIC.regul_id_seq " +
            " where id=:id", nativeQuery = true)
    int approveRegular(@Param("id") long id, @Param("date") LocalDateTime approveTime);

    @Transactional
    @Modifying
    @Query(value = "update Quote q set q.type=:type where q.id=:id")
    int switchType(@Param("id") long id, @Param("type") Type type);

    Quote findByCapsId(long id);

    Quote findByRegularId(long id);
}