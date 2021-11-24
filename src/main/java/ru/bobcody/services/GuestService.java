package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.Guest;
import ru.bobcody.repository.GuestRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class GuestService {
    @Autowired
    GuestRepository guestRepository;

    @Transactional
    @CacheEvict(value = {"guestById"}, allEntries = true)
    public Guest add(Guest guest) {
        log.info("save new guest {}", guest);
        return guestRepository.save(guest);
    }

    public List<Guest> getAll() {
        log.info("get all guests");
        return guestRepository.findAllBy();
    }

    public Guest findById(Long id) {
        log.info("try to find guest by id {}", id);
        return guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found by id " + id));
    }

    @Cacheable(value = "guestById", key = "#id")
    public boolean containGuest(Long id) {
        return guestRepository.findById(id).isPresent();
    }
}
