package ru.bobcody.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler.AbstractSpringBootStarterTest;
import ru.bobcody.entities.Guest;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.bobcody.services.data.GuestsData.*;

class GuestServiceTest extends AbstractSpringBootStarterTest {
    @Autowired
    GuestService guestService;

    {
        ignoreFields = new String[]{"textMessages"};
    }

    @Transactional
    @Test
    void add() {
        long unsavedId = VIKTOR.getId();
        assertThatThrownBy(() -> guestService.findById(unsavedId)).isInstanceOf(EntityNotFoundException.class);
        Guest guestSaved = guestService.add(VIKTOR);
        assertMatch(VIKTOR, guestSaved);
        assertMatch(VIKTOR, guestService.findById(unsavedId));
    }

    @Test
    void getAll() {
        List<Guest> dbList = guestService.getAll();
        assertThat(dbList).contains(SERGY, DMITRY, ADMIN);
        assertThat(dbList).hasSize(3);
    }

    @Test
    void findById() {
        assertMatchIgnoreFields(guestService.findById(SERGY.getId()), SERGY);
    }

    @Test
    void notFoundById() {
        assertThatThrownBy(() -> guestService.findById(8550L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void containGuestTrue() {
        assertThat(guestService.containGuest(SERGY.getId())).isTrue();
    }

    @Test
    void containGuestFalse() {
        assertThat(guestService.containGuest(3245L)).isFalse();
    }
}