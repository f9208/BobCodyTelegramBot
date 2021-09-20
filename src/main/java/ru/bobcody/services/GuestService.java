package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.Guest;
import ru.bobcody.repository.GuestRepository;

import java.util.List;

@Slf4j
@Service
public class GuestService {
    @Autowired
    GuestRepository guestRepository;

    @Transactional
    @CacheEvict(value = "guests", allEntries = true)
    public void add(Guest guest) {
        log.info("save new guest {}", guest);
        guestRepository.save(guest);
    }

    @Cacheable("guests")
    public List<Guest> getAll() {
        log.info("get all guests");
        return guestRepository.findAllBy();
    }

    //todo попробовать переделать на дергание из кэша
    public Guest findById(Long id) {
        log.info("try to find guest by id {}", id);
        return guestRepository.findGuestById(id);
    }

    public boolean comprise(long id) {
        return guestRepository.existsById(id);
    }
}
