package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.QuoteEntityStorage;
import ru.bobcody.repository.QuoteAbyssRepository;
import ru.bobcody.repository.QuoteStorageRepository;

import java.util.Calendar;

@Service
public class QuoteStorageService {
    @Autowired
    QuoteStorageRepository quoteStorageRepository;
    @Autowired
    QuoteAbyssRepository quoteAbyssRepository;

    @Transactional
    public void add(QuoteEntityStorage quoteEntityStorage) {
        quoteStorageRepository.save(quoteEntityStorage);
    }

    @Transactional
    public QuoteEntityStorage getSingleQuoteFromStorageById(Long id) {
        return quoteStorageRepository.getQuoteEntityStorageById(id);
    }

    @Transactional
    public Long adderQuote(Long a) {
        long currTime = Calendar.getInstance().getTime().getTime() / 1000;
        quoteAbyssRepository.approveQuote(a, currTime);
        return getMaxID();
    }

    @Transactional
    public Long getMaxID() {
        return quoteStorageRepository.getMaxID();
    }

    @Transactional
    public boolean containInQuoteStorage(Long abyssQuoteId) {
        Long dateAdded = quoteAbyssRepository.getDateAddedById(abyssQuoteId);
        return quoteStorageRepository.existsQuoteEntityStorageByDateAdded(dateAdded);
    }

    @Transactional
    public boolean existById(long id) {
        return quoteStorageRepository.existsById(id);
    }

    @Transactional
   //ручное добавление цитат из файла.
    public void handleAdd(Long key, String value) {
        quoteStorageRepository.handAdd(value, key);

    }
}