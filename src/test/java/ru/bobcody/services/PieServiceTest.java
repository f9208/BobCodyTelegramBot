package ru.bobcody.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler.AbstractSpringBootStarterTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PieServiceTest extends AbstractSpringBootStarterTest {
    @Autowired
    PieService pieService;

    @Test
    void getOne() throws IOException {
        assertThat(pieService.getOne().getShareText().length() > 30).isTrue();
    }
}