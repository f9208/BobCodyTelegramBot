package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.handlers.chathandlers.secondlayerhandler.AbstractSpringBootStarterTest;
import ru.bobcody.updates.handlers.chathandlers.MainHandlerTextMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.TelegramMessageData.TELEGRAM_MESSAGE_1;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TopHandlerTest extends AbstractSpringBootStarterTest {
    private final MainHandlerTextMessage mainHandlerTextMessage;

    @Test
    void handle() {
        TELEGRAM_MESSAGE_1.setText("!топ");
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer)
                .startsWith("Топ болтунов за все время:")
                .contains("1. Sergy");
    }
}