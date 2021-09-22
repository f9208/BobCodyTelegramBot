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
    public TextMessage saveInputMessage(TextMessage textMessage) {
        log.info("save input message: {}", textMessage.getTextMessage());
        return textMessageRepository.save(textMessage);
    }

    @Transactional
    public TextMessage saveOutputMessage(TextMessage outputMessage) {
        log.info("save output message: {}", outputMessage.getTextMessage());
        return textMessageRepository.save(outputMessage);
    }

    public TextMessage getById(long id) {
        log.info("try to get message by number {}", id);
        return textMessageRepository.findById(id).orElse(null);
    }

    public List<TextMessage> getForDateBetween(LocalDate start, LocalDate end) {
        log.info("try to get list of messages for dates between {} and {} ", start, end);
        return textMessageRepository.findAllByDateTimeBetween(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    }

    public List<LocalDate> getListDates() {
        return textMessageRepository.findAllDateTime()
                .stream()
                .map(Date::toLocalDate)
                .collect(Collectors.toList());
    }
}
