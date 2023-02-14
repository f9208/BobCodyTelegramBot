//package ru.bobcody.data.services;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.bobcody.controller.handlers.chathandlers.secondlayerhandler.AbstractSpringBootStarterTest;
//import ru.bobcody.domain.Quote;
//import ru.bobcody.services.QuoteService;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static ru.bobcody.data.services.manual.QuoteDate.*;
//
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//class QuoteServiceTest extends AbstractSpringBootStarterTest {
//    private final QuoteService quoteService;
//
//    {
//        ignoreFields = new String[]{"author"};
//    }
//
//    @Test
//    void save() {
//        Quote createCopy = new Quote(QUOTE_0_NEW);
//        Quote saved = quoteService.save(QUOTE_0_NEW);
//        createCopy.setId(saved.getId());
//        createCopy.setCreateDate(saved.getCreateDate());
//        assertMatchIgnoreFields(createCopy, quoteService.getById(saved.getId()));
//    }
//
//    @Test
//    void approveCaps() {
//        assertMatchIgnoreFields(quoteService.getById(QUOTE_ID_1), QUOTE_1_ABYSS);
//        assertThat(quoteService.approveCaps(QUOTE_ID_1)).isTrue();
//        Quote approved = quoteService.getById(QUOTE_ID_1);
//        QUOTE_1_APPROVED.setApprovedDate(approved.getApprovedDate()); //невозможно указать подставное время сохранения
//        assertMatchIgnoreFields(approved, QUOTE_1_APPROVED);
//    }
//
//    @Test
//    void approveRegular() {
//        assertMatchIgnoreFields(quoteService.getById(QUOTE_ID_2), QUOTE_2_ABYSS);
//        assertThat(quoteService.approveRegular(QUOTE_ID_2)).isTrue();
//        Quote approved = quoteService.getById(QUOTE_ID_2);
//        QUOTE_2_APPROVED.setApprovedDate(approved.getApprovedDate());
//        assertMatchIgnoreFields(quoteService.getById(QUOTE_ID_2), QUOTE_2_APPROVED);
//    }
//
//    @Test
//    void getRegularById() {
//        assertMatchIgnoreFields(quoteService.getByRegularId(1), QUOTE_3_APPROVED);
//    }
//
//    @Test
//    void getCapsById() {
//        assertMatchIgnoreFields(quoteService.getByCapsId(1), QUOTE_4_APPROVED);
//    }
//
//    @Test
//    void getRegularId() {
//        assertThat(QUOTE_3_APPROVED.getRegularId()).isEqualTo(quoteService.getRegularId(QUOTE_ID_3));
//    }
//
//    @Test
//    void getCapsId() {
//        assertThat(QUOTE_4_APPROVED.getRegularId()).isEqualTo(quoteService.getRegularId(QUOTE_ID_4));
//    }
//
//    @Test
//    void getById() {
//        assertMatchIgnoreFields(QUOTE_4_APPROVED, quoteService.getById(QUOTE_ID_4));
//
//    }
//
//    @Test
//    void getLastRegularId() {
//        assertThat(quoteService.getLastRegularId()).isEqualTo(1);
//        assertMatchIgnoreFields(quoteService.getById(QUOTE_ID_2), QUOTE_2_ABYSS);
//        assertThat(quoteService.approveRegular(QUOTE_ID_2)).isTrue();
//        assertThat(quoteService.getLastRegularId()).isEqualTo(2);
//    }
//
//    @Test
//    void getLastCapsId() {
//        assertThat(quoteService.getLastCapsId()).isEqualTo(1);
//        assertMatchIgnoreFields(quoteService.getById(QUOTE_ID_2), QUOTE_2_ABYSS);
//        assertTrue(quoteService.approveCaps(QUOTE_ID_2));
//        assertThat(quoteService.getLastCapsId()).isEqualTo(2);
//    }
//}