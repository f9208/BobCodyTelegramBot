package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.Quote;
import ru.bobcody.entities.Type;
import ru.bobcody.repository.QuoteRepository;

import java.time.LocalDateTime;

@Service
public class QuoteService {
    @Autowired
    QuoteRepository quoteRepository;

    @Transactional
    public Quote save(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Quote getById(long id) {
        return quoteRepository.findById(id).orElse(null);
    }

    @Transactional
    boolean approveCaps(long quoteId) {
        return quoteRepository.approveCaps(quoteId, LocalDateTime.now()) == 1;
    }

    @Transactional
    boolean approveRegular(long quoteId) {
        return quoteRepository.approveRegular(quoteId, LocalDateTime.now()) == 1;
    }

    @Transactional //хз зачем этот метод?
    boolean switchType(long quoteId, Type type) {
        return quoteRepository.switchType(quoteId, type) == 1;
    }


    public Quote getByCapsId(long id) {
        return quoteRepository.findByCapsId(id);
    }

    public Quote getByRegularId(long id) {
        return quoteRepository.findByRegularId(id);
    }
}
