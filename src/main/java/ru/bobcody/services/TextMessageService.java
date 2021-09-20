package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.TextMessage;
import ru.bobcody.repository.TextMessageRepository;

@Slf4j
@Service
public class TextMessageService {
    @Autowired
    TextMessageRepository textMessageRepository;

    @Transactional
    public TextMessage saveInputMessage(TextMessage textMessage) {
        log.info("save input message: {}", textMessage.getTextMessage());
        return textMessageRepository.save(textMessage);
    }

    @Transactional
    public TextMessage saveOutputMessage(TextMessage outputMessage) {
        log.info("save output message: {}", outputMessage.getTextMessage());
        return textMessageRepository.save(outputMessage);
    }

    public TextMessage get(long id) {
        log.info("try to get message by number {}", id);
        return textMessageRepository.findById(id).orElse(null);
    }
}
