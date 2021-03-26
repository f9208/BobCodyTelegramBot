package ru.multibot.bobcody.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.SQL.Entities.Guest;
import ru.multibot.bobcody.SQL.Entities.QuoteEntityAbyss;
import ru.multibot.bobcody.SQL.repository.GuestRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GuestServiceImp implements GuestService {
    @Autowired
    GuestRepository guestRepository;

    @Transactional
    @Override
    public void add(Guest guest) {
        guestRepository.save(guest);
    }

    @Override
    public boolean comprise(long id) {
        return guestRepository.existsById(id);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAllByOrderByUserID();
    }
}
