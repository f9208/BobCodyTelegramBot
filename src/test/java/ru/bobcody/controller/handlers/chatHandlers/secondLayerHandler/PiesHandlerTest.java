package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.controller.updates.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.PiesHandler;
import ru.bobcody.data.services.PieService;
import ru.bobcody.thirdPartyAPI.hotPies.SinglePie;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.bobcody.controller.handlers.chatHandlers.PropertiesUtils.getCommandsByKey;
import static ru.bobcody.services.data.TelegramMessageData.TELEGRAM_MESSAGE_1;

class PiesHandlerTest extends AbstractSpringBootStarterTest {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    PiesHandler piesHandler;
    private final static List<String> COMMANDS = getCommandsByKey("pies.command");
    PieService pieService = Mockito.mock(PieService.class);
    SinglePie pie = new SinglePie();

    void init() {
        pie.setShareURL("url://somwere.com");
        pie.setShareText("пирожок пирожок ура");
        try {
            when(pieService.getOne()).thenReturn(pie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        piesHandler.setPieService(pieService);
    }

    @ParameterizedTest
    @MethodSource("getCommands")
    void handle(String inputText) throws IOException {
        init();
        TELEGRAM_MESSAGE_1.setText(inputText);
        String answer = mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText();
        assertThat(answer).isEqualTo("пирожок пирожок ура\nurl://somwere.com");
    }

    @Test
    void ignore() {
        TELEGRAM_MESSAGE_1.setText("fweofpk");
        assertThat(mainHandlerTextMessage.handle(TELEGRAM_MESSAGE_1).getText()).isNull();
    }

    private static List<String> getCommands() {
        return COMMANDS;
    }
}