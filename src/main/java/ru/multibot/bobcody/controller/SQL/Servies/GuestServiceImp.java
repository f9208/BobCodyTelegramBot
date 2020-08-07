package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.repository.GuestRepository;

import java.util.Optional;

@Service
public class GuestServiceImp implements GuestService {
    @Autowired
    GuestRepository guestRepository;

    @Transactional
    @Override
    public void add(Guest guest) {
        guestRepository.save(guest);
    }

    @Transactional
    @Override
    public Guest deleteById(long id) {
        Optional<Guest> opt = guestRepository.findById((Long) id);
        Guest currentGuest = opt.get();
        guestRepository.deleteById((Long) id);

        return currentGuest;
    }

    @Transactional
    @Override
    public boolean comprise(long id) {
        return guestRepository.existsById((Long) id);

    }



}
