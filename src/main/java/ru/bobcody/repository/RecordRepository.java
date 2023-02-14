package ru.bobcody.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bobcody.domain.Record;
import ru.bobcody.domain.RecordType;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(nativeQuery = true,
            value = "select nextval('quote_sequence')")
    long getQuoteNextId();

    @Query(nativeQuery = true,
            value = "select nextval('caps_sequence')")
    long getCapsNextId();

    Optional<Record> findById(Long id);

    Optional<Record> findByParticularIdAndType(Long particularId, RecordType type);

    //    long countRecordByType(RecordType type);

    boolean existsByType(RecordType type);

    @Query(nativeQuery = true,
            value = "select max(particular_id) from #{#entityName} " +
                    " where type=:#{#type?.name()} ")
    long getMaxIdByType(@Param("type") RecordType type);

}