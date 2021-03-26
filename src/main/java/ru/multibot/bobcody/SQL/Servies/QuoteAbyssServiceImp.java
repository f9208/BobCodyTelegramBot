package ru.multibot.bobcody.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.SQL.Entities.QuoteEntityAbyss;
import ru.multibot.bobcody.SQL.repository.CapsQuoteStorageRepository;
import ru.multibot.bobcody.SQL.repository.QuoteAbyssRepository;
import ru.multibot.bobcody.SQL.repository.QuoteStorageRepository;

import java.util.Calendar;

@Service
@Transactional(readOnly = true)
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
    public Long getQuoteIdByDateAdded(Long date) {
        return quoteAbyssRepository.getQuoteIdByDate(date);
    }

    @Override
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

