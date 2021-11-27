package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.QuoteAbyss;
import ru.bobcody.repository.CapsQuoteStorageRepository;
import ru.bobcody.repository.QuoteAbyssRepository;
import ru.bobcody.repository.QuoteStorageRepository;

import java.util.Calendar;

@Service
public class QuoteAbyssService {
    @Autowired
    QuoteAbyssRepository quoteAbyssRepository;
    @Autowired
    QuoteStorageRepository quoteStorageRepository;
    @Autowired
    CapsQuoteStorageRepository capsQuoteStorageRepository;

    @Transactional
    public void add(QuoteAbyss quoteAbyss) {
        quoteAbyssRepository.save(quoteAbyss);
    }

    @Transactional
    public Long getQuoteIdByDateAdded(Long date) {
        return quoteAbyssRepository.getQuoteIdByDate(date);
    }

    @Transactional
    public boolean containInAbyss(Long id) {
        return quoteAbyssRepository.existsQuoteById(id);
    }

    @Transactional
    public Long approveQuote(Long id) {
        quoteAbyssRepository.approveQuote(id, Calendar.getInstance().getTime().getTime() / 1000);
        return quoteStorageRepository.getMaxID();
    }

    @Transactional
    public Long approveCaps(Long id) {
        quoteAbyssRepository.approveCaps(id, Calendar.getInstance().getTime().getTime() / 1000);
        return capsQuoteStorageRepository.getMaxID();
    }
}

