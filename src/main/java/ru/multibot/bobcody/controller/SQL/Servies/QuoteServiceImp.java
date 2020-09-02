package ru.multibot.bobcody.controller.SQL.Servies;

import com.sun.org.apache.xpath.internal.operations.Quo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;
import ru.multibot.bobcody.controller.SQL.repository.GuestRepository;
import ru.multibot.bobcody.controller.SQL.repository.QuoteRepository;

import java.util.List;

@Service
public class QuoteServiceImp implements QuoteService {
    @Autowired
    QuoteRepository quoteRepository;

    @Override
    @Transactional
    public void add(Quote quote) {
        quoteRepository.save(quote);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        quoteRepository.deleteById(id);
    }


}
