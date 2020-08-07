package ru.multibot.bobcody.controller.SQL.Servies;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.QuotationsBook;
import ru.multibot.bobcody.controller.SQL.repository.QuotationsBookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Service
public class QuotationsBookServiceImp implements QuotationsBookService {
    @Autowired
    QuotationsBookRepository quotationsBookRepository;

    @Transactional
    @Override
    public Long add(QuotationsBook quotationsBook) {
        quotationsBookRepository.save(quotationsBook);
        QuotationsBook time = quotationsBookRepository.findQuotationsBookByData(quotationsBook.getData());
        System.out.println(time.getAuthor());
        System.out.println(time.getQuotationId());
        return time.getQuotationId();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        quotationsBookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public QuotationsBook getById(Long id) {
        return null;
    }

    @Transactional
    public List<QuotationsBook> getAll() {
        Iterable<QuotationsBook> iter = quotationsBookRepository.findAll();
        List<QuotationsBook> list = new ArrayList<>();

        for (QuotationsBook q : iter) {
            list.add(q);
        }
        return list;
    }

}

