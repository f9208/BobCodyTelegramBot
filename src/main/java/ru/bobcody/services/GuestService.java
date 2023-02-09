package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bobcody.domain.Guest;
import ru.bobcody.repository.GuestRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    public Guest getGuest(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Guest with id=%s not found", id)));
    }
}
