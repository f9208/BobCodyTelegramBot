package ru.multibot.bobcody.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.SQL.Entities.Quote;
import ru.multibot.bobcody.SQL.repository.QuoteAbyssRepository;

@Service
public class QuoteAbyssServiceImp implements QuoteAbyssService {
    @Autowired
    QuoteAbyssRepository quoteAbyssRepository;

    @Override
    @Transactional
    public void add(Quote quote) {
        quoteAbyssRepository.save(quote);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        quoteAbyssRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Long getQuoteIdByDateAdded(Long date) {
        return quoteAbyssRepository.getQuoteIdByDate(date);
    }

    @Override
    @Transactional
    public boolean containtInAbyss(Long id) {
        return quoteAbyssRepository.existsQuoteByQuoteId(id);
    }

    @Override
    @Transactional
    public Long getDateAddedByQuoteId(Long quoteId) {
        return quoteAbyssRepository.getDateAddedByQuoteId(quoteId);
    }

    @Override
    @Transactional
    public Long getAuthorIdByQuoteId(Long quoteId) {
        return quoteAbyssRepository.getAuthorIdByQuoteId(quoteId);

    }
}
