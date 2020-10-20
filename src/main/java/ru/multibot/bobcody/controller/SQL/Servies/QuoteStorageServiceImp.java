package ru.multibot.bobcody.controller.SQL.Servies;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public QuoteInsideStorage getById(Long id) {
        return quoteStorageRepository.findById(id).get();
    }


    public int getSizeDB() {
        return quoteStorageRepository.getSizeDB();
    }


    public String getStringById(int id) {
//
//        сделать обработчики исключений здесь.
//        java.lang.NumberFormatException
//                + если вернули null.
        try {
            return quoteStorageRepository.getTextQuoteById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Long adderQuote(Long a) {
        long currTime = Calendar.getInstance().getTime().getTime() / 1000;
        quoteStorageRepository.approveQuote(a, currTime);
        return getMaxID();
    }

    @Transactional
    public Long getMaxID() {
        return quoteStorageRepository.getMaxID();
    }

    @Transactional
    public boolean existById(long id) {
        return quoteStorageRepository.existsByQuoteId(id);
    }

    @Transactional
    public boolean existByDate(long date) {
        return quoteStorageRepository.existsQuoteInsideStorageByDateAdded(date);
    }

    @Transactional
    public Long getAuthorByDateAdded(Long dateAdded) {
        return quoteStorageRepository.getAuthorByDateAdded(dateAdded);
    }
}

