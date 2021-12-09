package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.services.GuestService;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.services.data.GuestsData.SERGY;
import static ru.bobcody.services.data.TelegramMessageData.TELEGRAM_MESSAGE_2;

class SetCityHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    GuestService guestService;

    @Test
    void getCity() {
        TELEGRAM_MESSAGE_2.setText("!город   ");
        String replay = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText();
        assertThat(replay).isEqualTo("@" + SERGY.getUserName() + ", ваш дефолтный город - Vorkuta");
        assertThat(guestService.findById(SERGY.getId()).getCityName()).isEqualTo("Vorkuta");
    }

    @Transactional
    @Test
    void setCity() {
        TELEGRAM_MESSAGE_2.setText("!город      эхо");
        assertThat(guestService.findById(SERGY.getId()).getCityName()).isEqualTo("Vorkuta");
        String replay = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText();
        assertThat(replay).isEqualTo("@" + SERGY.getUserName() + ", теперь твой погодный город - эхо");
        assertThat(guestService.findById(SERGY.getId()).getCityName()).isEqualTo("эхо");
        TELEGRAM_MESSAGE_2.setText("!город      Vorkuta");
        mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_2).getText();
    }

    @AfterEach
    void sfwe(TestInfo i) {
        System.out.println(guestService.findById(SERGY.getId()).getCityName());
    }

}