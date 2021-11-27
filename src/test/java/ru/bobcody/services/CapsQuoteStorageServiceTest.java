package ru.bobcody.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler.AbstractSpringBootStarterTest;
import ru.bobcody.entities.CapsQuoteStorage;

import static org.junit.jupiter.api.Assertions.*;
import static ru.bobcody.services.data.CapsQuoteStorageData.*;
import static ru.bobcody.services.data.GuestsData.SERGY;

class CapsQuoteStorageServiceTest extends AbstractSpringBootStarterTest {
    @Autowired
    CapsQuoteStorageService capsQuoteStorageService;

    {
        ignoreFields = new String[]{"author"}; // author тащить с собой еще лишние коллекции, поэтому не вешаем транзацкию
    }

    @Test
    void existById() {
        assertTrue(capsQuoteStorageService.existById(CAPS_1_ID));
        assertFalse(capsQuoteStorageService.existById(1234L));
    }

    @Test
    void getById() {
        CapsQuoteStorage caps = capsQuoteStorageService.getById(CAPS_2_ID);
        assertMatchIgnoreFilds(caps, CAPS_QUOTE_S_2);
        assertEquals(caps.getAuthor(), SERGY);
        assertNull(capsQuoteStorageService.getById(123L));
    }

    @Test
    void containInCapsQuoteStorage() {
//        assertTrue(capsQuoteStorageService.containInCapsQuoteStorage(CAPS_3_ID)); //todo не находит потому что в бездне таких нет
        assertFalse(capsQuoteStorageService.containInCapsQuoteStorage(23414L)); //todo кинуть реальный айдишник из abysss
    }

    @Test
    void getMaxID() {
        assertEquals(capsQuoteStorageService.getMaxID(), CAPS_3_ID);
    }
}