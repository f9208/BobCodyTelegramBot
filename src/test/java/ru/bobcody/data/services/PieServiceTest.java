//package ru.bobcody.data.services;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import ru.bobcody.controller.handlers.chathandlers.secondlayerhandler.AbstractSpringBootStarterTest;
//import ru.bobcody.services.PieService;
//
//import java.io.IOException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class PieServiceTest extends AbstractSpringBootStarterTest {
//
//    private final PieService pieService;
//
//    public PieServiceTest(@Autowired
//                          @Qualifier("original") PieService pieService) {
//        this.pieService = pieService;
//    }
//
//    @Test
//    void getOne() throws IOException {
//        assertThat(pieService.getOne().getShareText().length() > 30).isTrue();
//    }
//}