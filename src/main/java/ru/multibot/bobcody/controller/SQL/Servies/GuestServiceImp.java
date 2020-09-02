package ru.multibot.bobcody.controller.SQL.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;
import ru.multibot.bobcody.controller.SQL.Entities.Quote;
import ru.multibot.bobcody.controller.SQL.repository.GuestRepository;

import java.util.List;
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
    public boolean comprise(long id) {
        return guestRepository.existsById((Long) id);

    }

    public List<Quote> findQuotes(long user_id){
        return guestRepository.findById(user_id).get().getQuotes();
    }


}
