package ru.bobcody.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler.AbstractSpringBootStarterTest;
import ru.bobcody.entities.Quote;
import ru.bobcody.entities.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bobcody.services.data.QuoteDate.*;

class QuoteServiceTest extends AbstractSpringBootStarterTest {
    @Autowired
    QuoteService quoteService;

    {
        ignoreFields = new String[]{"author"};
    }

    @Test
    void save() {
        Quote createCopy = new Quote(QUOTE_0_NEW);
        Quote saved = quoteService.save(QUOTE_0_NEW);
        createCopy.setId(saved.getId());
        assertMatchIgnoreFilds(createCopy, quoteService.getById(saved.getId()));
    }

    @Test
    void approveCaps() {
        assertMatchIgnoreFilds(quoteService.getById(QUOTE_ID_1), QUOTE_1_ABYSS);
        assertTrue(quoteService.approveCaps(QUOTE_ID_1));
        Quote approved = quoteService.getById(QUOTE_ID_1);
        QUOTE_1_APPROVED.setApproved(approved.getApproved()); //невозможно указать подставное время сохранения
        assertMatchIgnoreFilds(approved, QUOTE_1_APPROVED);
    }

    @Test
    void approveRegular() {
        assertMatchIgnoreFilds(quoteService.getById(QUOTE_ID_2), QUOTE_2_ABYSS);
        assertTrue(quoteService.approveRegular(QUOTE_ID_2));
        Quote approved = quoteService.getById(QUOTE_ID_2);
        QUOTE_2_APPROVED.setApproved(approved.getApproved());
        assertMatchIgnoreFilds(quoteService.getById(QUOTE_ID_2), QUOTE_2_APPROVED);
    }

    @Test
    void switchType() {
        assertEquals(quoteService.getById(QUOTE_ID_2).getType(), Type.ABYSS);
        quoteService.switchType(QUOTE_ID_2, Type.CAPS);
        assertEquals(quoteService.getById(QUOTE_ID_2).getType(), Type.CAPS);
    }

    @Test
    void getRegularById() {
        assertMatchIgnoreFilds(quoteService.getByRegularId(1), QUOTE_3_APPROVED);
    }

    @Test
    void getCapsById() {
        assertMatchIgnoreFilds(quoteService.getByCapsId(1), QUOTE_4_APPROVED);
    }

    @Test
    void getRegularId() {
    }

    @Test
    void getCapsId() {
    }
}