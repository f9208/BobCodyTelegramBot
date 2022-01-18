package ru.bobcody.data.services;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chathandlers.secondlayerhandler.AbstractSpringBootStarterTest;
import ru.bobcody.data.entities.TextMessage;
import ru.bobcody.data.services.manual.TelegramMessageData;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.ChatData.GROUP_CHAT;
import static ru.bobcody.data.services.manual.TextMessageData.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TextMessageServiceTest extends AbstractSpringBootStarterTest {
    private final TextMessageService textMessageService;

    {
        ignoreFields = new String[]{"guest", "chat"};
    }

    @BeforeEach
    void init() {
        TelegramMessageData.init();
    }

    @Test
    void saveInputMessage() {
        System.out.println(TEXT_MESSAGE_UNSAVED);
        int saved = textMessageService.saveInputMessage(TEXT_MESSAGE_UNSAVED);
        Assertions.assertThat(saved).isEqualTo(1);
    }

    @Test
    void getById() {
        TextMessage tmFromDb = textMessageService.getById(3);
        assertMatchIgnoreFields(tmFromDb, TEXT_MESSAGE_3);
    }

    @Test
    void getOnDateBetweenForChat() {
        LocalDate start = LocalDate.parse("2020-12-12");
        LocalDate end = LocalDate.parse("2021-11-20");
        List<TextMessage> result =
                textMessageService.getOnDateBetweenForChat(start, end, GROUP_CHAT.getId());
        assertThat(result)
                .hasSize(2)
                .contains(TEXT_MESSAGE_2)
                .doesNotContain(TEXT_MESSAGE_4);
    }
    //todo
//    @Test
//        //валится потому что native query не умеет в hsqldb. переделать
//    void getListDatesForChat() {
//        List<LocalDate> result = textMessageService.getListDatesForChat(GROUP_CHAT.getId());
//        assertThat(result)
//                .containsOnly(
//                        LocalDate.ofEpochDay(TELEGRAM_MESSAGE_1_DATE),
//                        LocalDate.ofEpochDay(TELEGRAM_MESSAGE_2_DATE),
//                        LocalDate.ofEpochDay(TELEGRAM_MESSAGE_4_DATE));
//    }

    @Transactional
    @Test
    void prepareAndSave() {
        int result = textMessageService.prepareAndSave(TEXT_MESSAGE_UNSAVED);
        assertThat(result).isEqualTo(1);
    }
}