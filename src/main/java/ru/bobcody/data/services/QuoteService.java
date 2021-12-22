package ru.bobcody.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.data.entities.Quote;
import ru.bobcody.data.repository.IQuoteRepository;

import java.time.LocalDateTime;

@Service
public class QuoteService {
    @Autowired
    private IQuoteRepository quoteRepository;

    @Transactional
    public Quote save(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Quote getById(long id) {
        return quoteRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean approveCaps(long quoteId) {
        return quoteRepository.approveCapsPostgresSQL(quoteId, LocalDateTime.now()) == 1;
    }

    @Transactional
    public boolean approveRegular(long quoteId) {
        return quoteRepository.approveRegularPostgreSql(quoteId, LocalDateTime.now()) == 1;
    }

    public Quote getByCapsId(long id) {
        return quoteRepository.findByCapsId(id);
    }

    public Quote getByRegularId(long id) {
        return quoteRepository.findByRegularId(id);
    }

    public long getRegularId(long id) {
        return getById(id).getRegularId();
    }

    public long getCapsId(long id) {
        return getById(id).getCapsId();
    }

    public long getLastRegularId() {
        return quoteRepository.lastRegularId();
    }

    public long getLastCapsId() {
        return quoteRepository.lastCapsId();
    }
}
