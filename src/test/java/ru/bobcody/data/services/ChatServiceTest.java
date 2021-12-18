package ru.bobcody.data.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler.AbstractSpringBootStarterTest;
import ru.bobcody.data.entities.Chat;

import static org.assertj.core.api.Assertions.assertThat;

import static ru.bobcody.data.services.manual.ChatData.*;

class ChatServiceTest extends AbstractSpringBootStarterTest {
    @Autowired
    ChatService chatService;

    {
        ignoreFields = new String[]{};
    }

    @Transactional //эта аннотация в _тестах_ откатывает БД в предыдущее состояние после выполнения теста
    @Test        //assertMatchIgnoreField не использую т.к транзакция открыта и LAZY поля могут загрузиться
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