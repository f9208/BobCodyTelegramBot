package ru.bobcody.data.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chathandlers.secondlayerhandler.AbstractSpringBootStarterTest;
import ru.bobcody.domain.Guest;
import ru.bobcody.data.services.manual.GuestsData;
import ru.bobcody.services.GuestService;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.bobcody.data.services.manual.GuestsData.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class GuestServiceTest extends AbstractSpringBootStarterTest {
    private final GuestService guestService;

    {
        ignoreFields = new String[]{"textMessages"};
    }

    @Transactional
    @Test
    void add() {
        long unsavedId = VIKTOR.getId();
        assertThatThrownBy(() -> guestService.getGuest(unsavedId)).isInstanceOf(EntityNotFoundException.class);
        Guest guestSaved = guestService.add(VIKTOR);
        assertMatch(VIKTOR, guestSaved);
        assertMatch(VIKTOR, guestService.getGuest(unsavedId));
    }


    @Test
    void findById() {
        GuestsData.init();
        assertMatchIgnoreFields(guestService.getGuest(SERGY.getId()), SERGY);
    }

    @Test
    void notFoundById() {
        assertThatThrownBy(() -> guestService.getGuest(8550L)).isInstanceOf(EntityNotFoundException.class);
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
        assertThat(guestService.getGuest(ADMIN.getId())).isEqualTo(ADMIN);
        Guest changedAdmin = new Guest(ADMIN.getId(), "Заяц", "туц", null, "tu");
        guestService.update(changedAdmin);
        assertThat(guestService.getGuest(ADMIN.getId())).isEqualTo(changedAdmin);
    }
}