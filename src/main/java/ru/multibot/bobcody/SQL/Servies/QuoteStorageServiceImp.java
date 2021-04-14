package ru.multibot.bobcody.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.SQL.Entities.QuoteEntityStorage;
import ru.multibot.bobcody.SQL.repository.QuoteAbyssRepository;
import ru.multibot.bobcody.SQL.repository.QuoteStorageRepository;

import java.util.Calendar;

@Service
@Transactional(readOnly = true)
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

    @Override
    public Long getMaxID() {
        return quoteStorageRepository.getMaxID();
    }

    @Override
    public boolean containInQuoteStorage(Long abyssQuoteId) {
        Long dateAdded = quoteAbyssRepository.getDateAddedByQuoteId(abyssQuoteId);
        return quoteStorageRepository.existsQuoteEntityStorageByDateAdded(dateAdded);
    }

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