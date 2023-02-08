package ru.bobcody.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.domain.Guest;
import ru.bobcody.repository.GuestRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
//@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

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

    @Cacheable(value = "guestById", key = "#id")
    public Guest findById(Long id) {
        log.info("try to find guest by id {}", id);
        return guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found by id " + id));
    }

    public boolean containGuest(Long id) {
        return guestRepository.findById(id).isPresent();
    }

    @CacheEvict(value = {"guestById"}, allEntries = true)
    public int update(Guest guest) {
        return guestRepository.update(guest.getId(),
                guest.getFirstName(),
                guest.getUserName(),
                guest.getLastName(), guest.getLanguageCode());
    }
}
