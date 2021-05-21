package ru.bobcody.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.Entities.Guest;
import ru.bobcody.repository.GuestRepository;

import java.util.List;

@Service
public class GuestService {
    @Autowired
    GuestRepository guestRepository;

    @Transactional
    public void add(Guest guest) {
        guestRepository.save(guest);
    }

    @Transactional
    public boolean comprise(long id) {
        return guestRepository.existsById(id);

    }

    @Transactional
    public List<Guest> getAllGuests() {
        return guestRepository.findAllByOrderById();
    }

}
