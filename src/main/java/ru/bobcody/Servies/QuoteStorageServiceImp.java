package ru.bobcody.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.Entities.QuoteEntityStorage;
import ru.bobcody.repository.QuoteAbyssRepository;
import ru.bobcody.repository.QuoteStorageRepository;

import java.util.Calendar;

@Service
public class QuoteStorageServiceImp implements QuoteStorageService {
    @Autowired
    QuoteStorageRepository quoteStorageRepository;
    @Autowired
    QuoteAbyssRepository quoteAbyssRepository;

    @Transactional
    @Override
    public void add(QuoteEntityStorage quoteEntityStorage) {
        quoteStorageRepository.save(quoteEntityStorage);
    }

    @Transactional
    @Override
    public QuoteEntityStorage getSingleQuoteFromStorageById(Long id) {
        return quoteStorageRepository.getQuoteEntityStorageByQuoteId(id);
    }

    @Override
    @Transactional
    public Long adderQuote(Long a) {
        long currTime = Calendar.getInstance().getTime().getTime() / 1000;
        quoteAbyssRepository.approveQuote(a, currTime);
        return getMaxID();
    }

    @Transactional
    @Override
    public Long getMaxID() {
        return quoteStorageRepository.getMaxID();
    }

    @Transactional
    @Override
    public boolean containInQuoteStorage(Long abyssQuoteId) {
        Long dateAdded = quoteAbyssRepository.getDateAddedByQuoteId(abyssQuoteId);
        return quoteStorageRepository.existsQuoteEntityStorageByDateAdded(dateAdded);
    }

    @Transactional
    @Override
    public boolean existById(long id) {
        return quoteStorageRepository.existsByQuoteId(id);
    }

    @Transactional
    @Override //ручное добавление цитат из файла.
    public void handleAdd(Long key, String value) {
        quoteStorageRepository.handAdd(value, key);

    }
}