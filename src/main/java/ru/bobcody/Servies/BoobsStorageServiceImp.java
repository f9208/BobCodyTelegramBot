package ru.bobcody.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.Entities.BoobsStorage;
import ru.bobcody.repository.BoobsStorageRepository;

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
