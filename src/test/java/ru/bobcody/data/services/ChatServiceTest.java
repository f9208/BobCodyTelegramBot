package ru.bobcody.data.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chathandlers.secondlayerhandler.AbstractSpringBootStarterTest;
import ru.bobcody.domain.Chat;
import ru.bobcody.services.ChatService;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bobcody.data.services.manual.ChatData.GROUP_CHAT;
import static ru.bobcody.data.services.manual.ChatData.UNSAVED_CHAT;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ChatServiceTest extends AbstractSpringBootStarterTest {
    private final ChatService chatService;

    {
        ignoreFields = new String[]{};
    }

    @Transactional //эта аннотация в _тестах_ откатывает БД в предыдущее состояние после выполнения теста
    @Test
        //assertMatchIgnoreField не использую т.к транзакция открыта и LAZY поля могут загрузиться
    void save() {
        long unsavedId = UNSAVED_CHAT.getId();
        Chat saved = chatService.save(UNSAVED_CHAT);
        assertMatch(UNSAVED_CHAT, chatService.getChatById(unsavedId));
        assertMatch(saved, UNSAVED_CHAT);
    }

    @Test
    void containChat() {
        assertThat(chatService.containChat(GROUP_CHAT.getId())).isTrue();
    }
}