package ru.bobcody.data.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.data.entities.Chat;
import ru.bobcody.data.repository.IChatRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {
    private final IChatRepository chatRepository;

    @Transactional
    @CacheEvict(value = "chatById", allEntries = true)
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id).orElse(new Chat());
    }

    @Cacheable(value = "chatById", key = "#id")
    public boolean containChat(Long id) {
        return chatRepository.findById(id).isPresent();
    }
}
