package ru.bobcody.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.Entities.QuoteEntityAbyss;
import ru.bobcody.repository.CapsQuoteStorageRepository;
import ru.bobcody.repository.QuoteAbyssRepository;
import ru.bobcody.repository.QuoteStorageRepository;

import java.util.Calendar;

@Service
public class QuoteAbyssServiceImp implements QuoteAbyssService {
    @Autowired
    QuoteAbyssRepository quoteAbyssRepository;
    @Autowired
    QuoteStorageRepository quoteStorageRepository;
    @Autowired
    CapsQuoteStorageRepository capsQuoteStorageRepository;


    @Override
    @Transactional
    public void add(QuoteEntityAbyss quoteEntityAbyss) {
        quoteAbyssRepository.save(quoteEntityAbyss);
    }

    @Override
    @Transactional
    public Long getQuoteIdByDateAdded(Long date) {
        return quoteAbyssRepository.getQuoteIdByDate(date);
    }

    @Override
    @Transactional
    public boolean containInAbyss(Long id) {
        return quoteAbyssRepository.existsQuoteByQuoteId(id);
    }


    @Transactional
    @Override
    public Long approveQuote(Long id) {
        quoteAbyssRepository.approveQuote(id, Calendar.getInstance().getTime().getTime() / 1000);
        return quoteStorageRepository.getMaxID();
    }

    @Transactional
    @Override
    public Long approveCaps(Long id) {
        quoteAbyssRepository.approveCaps(id, Calendar.getInstance().getTime().getTime() / 1000);
        return capsQuoteStorageRepository.getMaxID();
    }

}

