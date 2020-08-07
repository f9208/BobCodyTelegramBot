package ru.multibot.bobcody.controller.SQL.Servies;

import ru.multibot.bobcody.controller.SQL.Entities.QuotationsBook;

public interface QuotationsBookService {
    public Long add(QuotationsBook quotationsBook);
    public void deleteById(Long id);
    public QuotationsBook getById(Long id);


}
