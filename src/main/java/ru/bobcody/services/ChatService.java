package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.Chat;
import ru.bobcody.repository.ChatRepository;

@Service
@Transactional(readOnly = true)
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Transactional
    public long save(Chat chat) {
        return chatRepository.save(chat).getId();
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id).orElse(new Chat());
    }
}
