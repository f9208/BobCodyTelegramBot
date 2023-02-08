package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.updates.handlers.chathandlers.MainHandlerTextMessage;
import ru.bobcody.services.GuestService;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.GuestsData.SERGY;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_2;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SetCityHandlerTest extends AbstractSpringBootStarterTest {
    private final MainHandlerTextMessage mainHandlerTextMessage;
    private final GuestService guestService;

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
}