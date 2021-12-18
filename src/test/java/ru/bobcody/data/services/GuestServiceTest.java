package ru.bobcody.data.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler.AbstractSpringBootStarterTest;
import ru.bobcody.data.entities.Guest;
import ru.bobcody.data.services.manual.GuestsData;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.bobcody.data.services.manual.GuestsData.*;

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
        GuestsData.init();
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

    @Test
    void updateGuest() {
        assertThat(guestService.findById(ADMIN.getId())).isEqualTo(ADMIN);
        Guest changedAdmin = new Guest(ADMIN.getId(), "Заяц", "туц", null, "tu");
        guestService.update(changedAdmin);
        assertThat(guestService.findById(ADMIN.getId())).isEqualTo(changedAdmin);
    }
}