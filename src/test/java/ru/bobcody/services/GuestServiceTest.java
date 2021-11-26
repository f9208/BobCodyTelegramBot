package ru.bobcody.services;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler.AbstractSpringBootStarterTest;
import ru.bobcody.entities.Guest;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static ru.bobcody.services.data.GuestsData.*;

class GuestServiceTest extends AbstractSpringBootStarterTest {
    @Autowired
    GuestService guestService;

    @Transactional
    @Test
    void add() {
        long unsavedId = VIKTOR.getId();
        assertThrows(EntityNotFoundException.class, () -> guestService.findById(unsavedId));
        Guest guestSaved = guestService.add(VIKTOR);
        assertEquals(VIKTOR, guestSaved); //todo ну такое себе. посмотреть как сравнивать обьекты
        assertEquals(VIKTOR, guestService.findById(unsavedId));
    }

    @Test
    void getAll() {
        List<Guest> dbList = guestService.getAll();
        Assertions.assertThat(dbList).contains(SERGY,DMITRY);
        Assertions.assertThat(dbList).hasSize(2);
    }

    @Test
    void findById() {

        assertEquals(guestService.findById(SERGY.getId()), SERGY); //todo тоже ну такое
    }

    @Test
    void notFoundById() {
        assertThrows(EntityNotFoundException.class, () -> guestService.findById(8550L));
    }

    @Test
    void containGuestTrue() {
        assertTrue(guestService.containGuest(SERGY.getId()));
    }

    @Test
    void containGuestFalse() {
        assertFalse(guestService.containGuest(3245L));
    }
}