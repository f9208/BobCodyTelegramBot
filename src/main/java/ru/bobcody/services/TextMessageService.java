package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.entities.TextMessage;
import ru.bobcody.repository.TextMessageRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TextMessageService {
    @Autowired
    TextMessageRepository textMessageRepository;

    @Transactional
    public int saveInputMessage(TextMessage textMessage) {
        log.info("save input messages: {}", textMessage.getTextMessage());
        return prepareAndSave(textMessage);
    }

    @Transactional
    public int saveOutputMessage(TextMessage outputMessage) {
        log.info("save output messages: {}", outputMessage.getTextMessage());
        return prepareAndSave(outputMessage);
    }

    public TextMessage getById(long id) {
        log.info("try to get messages by number {}", id);
        return textMessageRepository.findById(id).orElse(null);
    }

    public List<TextMessage> getOnDateBetweenForChat(LocalDate start, LocalDate end, long chatId) {
        log.info("try to get list of messages for dates between {} and {} and chatId {}", start, end, chatId);
        return textMessageRepository.findAllByDateTimeBetweenAndChatIdOrderByDateTime(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), chatId);
    }

    public List<LocalDate> getListDatesForChat(long chatId) {
        return textMessageRepository.findAllDateTime(chatId)
                .stream()
                .map(Date::toLocalDate)
                .collect(Collectors.toList());
    }

    @Transactional
    int prepareAndSave(TextMessage message) {
        return textMessageRepository.saveOne(message.getDateTime(),
                message.getTelegram(),
                message.getTextMessage(),
                message.getChat().getId(),
                message.getGuest().getId());
    }
}
