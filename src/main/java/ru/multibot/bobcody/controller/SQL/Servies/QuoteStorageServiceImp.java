package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteStorage;
import ru.multibot.bobcody.controller.SQL.repository.QuoteStorageRepository;

@Service
public class QuoteStorageServiceImp implements QuoteStorageService {
    @Autowired
    QuoteStorageRepository quoteStorageRepository;

    @Transactional
    @Override
    public void add(QuoteStorage quoteStorage) {
        quoteStorageRepository.save(quoteStorage);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        quoteStorageRepository.deleteById(id);
    }

    @Transactional
    @Override
    public QuoteStorage getById(Long id) {
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
    public int adderQuote(int a) {
        quoteStorageRepository.approveQuote(a);
        return getMaxID();
    }

    @Transactional
    public int getMaxID() {
        return quoteStorageRepository.getMaxID();
    }

    @Transactional
    public boolean existById(long id) {
        return quoteStorageRepository.existsByQuotationId(id);
    }
}

