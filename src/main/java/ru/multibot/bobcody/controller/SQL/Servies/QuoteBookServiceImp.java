package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.QuoteBook;
import ru.multibot.bobcody.controller.SQL.repository.QuoteBookRepository;

@Service
public class QuoteBookServiceImp implements QuoteBookService {
    @Autowired
    QuoteBookRepository quoteBookRepository;

    @Transactional
    @Override
    public void add(QuoteBook quoteBook) {
        quoteBookRepository.save(quoteBook);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        quoteBookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public QuoteBook getById(Long id) {
        return  quoteBookRepository.findById(id).get();
    }

    public int getSizeDB() {

        return quoteBookRepository.getSizeDB();
    }

    public String getStringById(int id) {
//
//        сделать обработчики исключений здесь.
//        java.lang.NumberFormatException
//                + если вернули null.
        try {
            return quoteBookRepository.iii(id);
        } catch (Exception e) {
            return null;
        }
    }

}

