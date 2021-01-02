package ru.multibot.bobcody.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.SQL.Entities.CapsQuoteEntityStorage;
import ru.multibot.bobcody.SQL.repository.CapsQuoteStorageRepository;
import ru.multibot.bobcody.SQL.repository.QuoteAbyssRepository;

@Service
public class CapsQuoteStorageServiceImp implements CapsQuoteStorageService {
    @Autowired
    CapsQuoteStorageRepository capsQuoteStorageRepository;
    @Autowired
    QuoteAbyssRepository quoteAbyssRepository;

    @Transactional
    @Override
    public boolean existById(Long id) {
        return capsQuoteStorageRepository.existsCapsQuoteEntityStorageByCapsQuoteID(id);
    }

    @Transactional
    @Override
    public CapsQuoteEntityStorage getById(Long id) {
        return capsQuoteStorageRepository.getCapsQuoteEntityStorageByCapsQuoteID(id);
    }

    @Transactional
    @Override
    public boolean containInCapsQuoteStorage(Long abyssQuoteId) {
        Long dateAdded = quoteAbyssRepository.getDateAddedByQuoteId(abyssQuoteId);
        return capsQuoteStorageRepository.existsCapsQuoteEntityStorageByDateAdded(dateAdded);
    }

    @Transactional
    @Override
    public Long getMaxID() {
        return capsQuoteStorageRepository.getMaxID();
    }

    @Transactional
    @Override
    public void handleAdd(Long key, String value){
        capsQuoteStorageRepository.handAdd(key, value);
    }
}
