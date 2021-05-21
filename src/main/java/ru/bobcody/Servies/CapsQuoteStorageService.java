package ru.bobcody.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.Entities.CapsQuoteEntityStorage;
import ru.bobcody.repository.CapsQuoteStorageRepository;
import ru.bobcody.repository.QuoteAbyssRepository;

@Service
public class CapsQuoteStorageService {
    @Autowired
    CapsQuoteStorageRepository capsQuoteStorageRepository;
    @Autowired
    QuoteAbyssRepository quoteAbyssRepository;

    @Transactional
    public boolean existById(Long id) {
        return capsQuoteStorageRepository.existsCapsQuoteEntityStorageByCapsQuoteID(id);
    }

    @Transactional
    public CapsQuoteEntityStorage getById(Long id) {
        return capsQuoteStorageRepository.getCapsQuoteEntityStorageByCapsQuoteID(id);
    }

    @Transactional
    public boolean containInCapsQuoteStorage(Long abyssQuoteId) {
        Long dateAdded = quoteAbyssRepository.getDateAddedById(abyssQuoteId);
        return capsQuoteStorageRepository.existsCapsQuoteEntityStorageByDateAdded(dateAdded);
    }

    @Transactional
    public Long getMaxID() {
        return capsQuoteStorageRepository.getMaxID();
    }

    @Transactional
    public void handleAdd(Long key, String value){
        capsQuoteStorageRepository.handAdd(key, value);
    }
}
