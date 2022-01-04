package ru.bobcody.data.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.data.entities.TextMessage;
import ru.bobcody.data.repository.ITextMessageRepository;

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
    private ITextMessageRepository textMessageRepository;

    @Transactional
    public int saveInputMessage(TextMessage textMessage) {
        log.info("save input messages: {}", textMessage.getText());
        return prepareAndSave(textMessage);
    }

    @Transactional
    public int saveOutputMessage(TextMessage outputMessage) {
        log.info("save output messages: {}", outputMessage.getText());
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
    public int prepareAndSave(TextMessage message) {
        return textMessageRepository.saveOne(message.getDateTime(),
                message.getTelegram(),
                message.getText(),
                message.getChat().getId(),
                message.getGuest().getId());
    }
//todo написать на это дело тесты
    public List<String> getTop(long chatId, LocalDateTime since, LocalDateTime to) {
        return textMessageRepository.getTop(chatId, since, to);
    }
}
