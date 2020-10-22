package ru.multibot.bobcody.controller.SQL.Servies;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteInsideStorage;
import ru.multibot.bobcody.controller.SQL.repository.QuoteStorageRepository;

import java.util.Calendar;

@Service
public class QuoteStorageServiceImp implements QuoteStorageService {
    @Autowired
    QuoteStorageRepository quoteStorageRepository;

    @Transactional
    @Override
    public void add(QuoteInsideStorage quoteInsideStorage) {
        quoteStorageRepository.save(quoteInsideStorage);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        quoteStorageRepository.deleteById(id);
    }

    @Transactional
    @Override
    public QuoteInsideStorage getSingleQuoteFromStorageById(Long id) {
        return quoteStorageRepository.getQuoteInsideStorageByQuoteId(id);
    }

    @Override
    @Transactional
    public int getSizeDB() {
        return quoteStorageRepository.getSizeDB();
    }

    @Override
    @Transactional
    public Long adderQuote(Long a) {
        long currTime = Calendar.getInstance().getTime().getTime() / 1000;
        quoteStorageRepository.approveQuote(a, currTime);
        return getMaxID();
    }

    @Transactional
    @Override
    public Long getMaxID() {
        return quoteStorageRepository.getMaxID();
    }

    @Transactional
    @Override
    public boolean existById(long id) {
        return quoteStorageRepository.existsByQuoteId(id);
    }

    @Transactional
    @Override
    public boolean existByDate(long date) {
        return quoteStorageRepository.existsQuoteInsideStorageByDateAdded(date);
    }

    @Transactional
    @Override
    public Long getAuthorByDateAdded(Long dateAdded) {
        return quoteStorageRepository.getAuthorByDateAdded(dateAdded);
    }


}

