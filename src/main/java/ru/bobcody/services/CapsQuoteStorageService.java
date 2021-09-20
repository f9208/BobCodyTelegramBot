package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.CapsQuoteStorage;
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
        return capsQuoteStorageRepository.existsCapsQuoteEntityStorageById(id);
    }

    @Transactional
    public CapsQuoteStorage getById(Long id) {
        return capsQuoteStorageRepository.getCapsQuoteEntityStorageById(id);
    }

    @Transactional
    public boolean containInCapsQuoteStorage(Long abyssQuoteId) {
        Long dateAdded = quoteAbyssRepository.getDateAddedById(abyssQuoteId);
        return capsQuoteStorageRepository.existsCapsQuoteEntityStorageByDateAdded(dateAdded);
    }

    @Transactional(readOnly = true)
    public Long getMaxID() {
        return capsQuoteStorageRepository.getMaxID();
    }

    @Transactional
    public void manualAdd(Long key, String value){
        capsQuoteStorageRepository.manualAdd(key, value);
    }
}
