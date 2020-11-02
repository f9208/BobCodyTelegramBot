package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.BoobsStorage;
import ru.multibot.bobcody.controller.SQL.repository.BoobsStorageRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoobsStorageServiceImp implements BoobsStorageService {
    @Autowired
    BoobsStorageRepository boobsStorageRepository;

    @Override
    @Transactional
    public void add(BoobsStorage boobsStorage) {
        boobsStorageRepository.save(boobsStorage);
    }

    @Override
    @Transactional
    public String getById(Long id) throws NoSuchElementException {
        return boobsStorageRepository.findById(id).get().getLink();
    }

    @Override
    @Transactional
    public Long getSizeDB() {
        return boobsStorageRepository.count();
    }

    @Override
    @Transactional
    public Iterable<BoobsStorage> getAllAsIterator() {
        return boobsStorageRepository.findAll();
    }

    @Transactional
    public Long findIdByLink(BoobsStorage boobsStorage) {
        return boobsStorageRepository.getBoobsStorageByLink(boobsStorage.getLink()).getId();
    }
}
