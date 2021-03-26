package ru.multibot.bobcody.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.SQL.Entities.BoobsStorage;
import ru.multibot.bobcody.SQL.repository.BoobsStorageRepository;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class BoobsStorageServiceImp implements BoobsStorageService {
    @Autowired
    BoobsStorageRepository boobsStorageRepository;

    @Override
    @Transactional
    public void add(BoobsStorage boobsStorage) {
        boobsStorageRepository.save(boobsStorage);
    }

    @Override
    public String getById(Long id) throws NoSuchElementException {
        return boobsStorageRepository.findById(id).get().getLink();
    }

    @Override
    public Long getSizeDB() {
        return boobsStorageRepository.count();
    }

    @Override
    public Iterable<BoobsStorage> getAllAsIterator() {
        return boobsStorageRepository.findAll();
    }

    public Long findIdByLink(BoobsStorage boobsStorage) {
        return boobsStorageRepository.getBoobsStorageByLink(boobsStorage.getLink()).getId();
    }
}
