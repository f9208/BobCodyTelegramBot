package ru.bobcody.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bobcody.entities.CapsQuoteStorage;

@Repository
public interface CapsQuoteStorageRepository extends CrudRepository<CapsQuoteStorage, Long> {

    @Query(value = "SELECT count(*) FROM public.capsquote_abyss", nativeQuery = true)
    int getSizeDB();

    CapsQuoteStorage getCapsQuoteEntityStorageByCapsQuoteID(Long id);

    @Query(value = "SELECT MAX (caps_quoteid) from public.caps_quotation_storage",
            nativeQuery = true)
    Long getMaxID();

    boolean existsCapsQuoteEntityStorageByCapsQuoteID(Long id);

    @Override
    CapsQuoteStorage save(CapsQuoteStorage entity);

    boolean existsCapsQuoteEntityStorageByDateAdded(Long id);

    @Modifying
    @Query(value = "INSERT INTO public.caps_quotation_storage(author, date_added, date_approved, caps_text)" +
            "SELECT 445682905," +
            ":currentTime, :currentTime, :textQuote", nativeQuery = true)
    void manualAdd(@Param("currentTime") Long unixTime, @Param("textQuote") String inputText);

}
