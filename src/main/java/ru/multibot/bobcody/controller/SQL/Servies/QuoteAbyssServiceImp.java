package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;
import ru.multibot.bobcody.controller.SQL.repository.QuoteAbyssRepository;

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


}
