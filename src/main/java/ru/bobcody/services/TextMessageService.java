package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.bobcody.domain.Chat;
import ru.bobcody.domain.Guest;
import ru.bobcody.domain.TextMessage;
import ru.bobcody.repository.TextMessageRepository;
import ru.bobcody.updates.handlers.chathandlers.MainHandlerTextMessage;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static ru.bobcody.utilits.CommonTextConstant.SMTH_GET_WRONG;

@Slf4j
@Service
public class TextMessageService {
    @Autowired
    private TextMessageRepository textMessageRepository;
//    @Autowired
    private MainHandlerTextMessage mainHandlerTextMessage;

    @Value("${chatid.admin}")
    private String adminChatId;

    private final Guest botAsGuest = new Guest(0L, "Bob", "Cody", "BobCody", "binary");


    @Transactional
    @Deprecated // сохранять через  saveTelegramMessage()
    public int saveInputMessage(TextMessage textMessage) {
        log.info("save input messages: {}", textMessage.getText());
//        return prepareAndSave(textMessage);
        return 43;
    }

    @Transactional
    @Deprecated // сохранять через  saveTelegramMessage()
    public int saveOutputMessage(TextMessage outputMessage) {
        log.info("save output messages: {}", outputMessage.getText());
//        return prepareAndSave(outputMessage);
        return 34;
    }

//    public TextMessage getById(long id) {
//        log.info("try to get messages by number {}", id);
//        return textMessageRepository.findById(id).orElse(null);
//    }

//    public List<TextMessage> getOnDateBetweenForChat(LocalDate start, LocalDate end, long chatId) {
//        log.info("try to get list of messages for dates between {} and {} and chatId {}", start, end, chatId);
//        return textMessageRepository.findAllByDateTimeBetweenAndChatIdOrderByDateTime(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), chatId);
//    }

//    public List<LocalDate> getListDatesForChat(long chatId) {
//        return textMessageRepository.findAllDateTime(chatId)
//                .stream()
//                .map(Date::toLocalDate)
//                .collect(Collectors.toList());
//    }

    //todo написать на это дело тесты
    public List<String> getTop(long chatId, LocalDateTime since, LocalDateTime to) {
        return textMessageRepository.getTop(chatId, since, to);
    }

    public SendMessage replayInputMessage(org.telegram.telegrambots.meta.api.objects.Message message, boolean edited) {

        saveTelegramMessage(message);

        SendMessage sendMessageReplay = new SendMessage();

        if (edited) {
            return sendMessageReplay;
        }

        try {
//            sendMessageReplay = mainHandlerTextMessage.handle(message);
            sendMessageReplay.setText("пидора ответ");
            sendMessageReplay.setChatId(message.getChatId());

        } catch (Exception e) {
            e.printStackTrace();
            sendMessageReplay.setText(SMTH_GET_WRONG + ": " + e.toString());
            sendMessageReplay.setChatId(adminChatId);
        }

        // Сохраняем ответ, т.к. бот не видит сообщения других ботов, в том числе и свои
        saveTelegramSendMessage(sendMessageReplay);

        return sendMessageReplay;
    }

    private TextMessage saveTelegramMessage(org.telegram.telegrambots.meta.api.objects.Message message) {
        TextMessage textMessage = new TextMessage();

        textMessage.setTelegramId((long) message.getMessageId());
        textMessage.setDateTime(LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .format(new Date(message.getDate().longValue() * 1000))));
        textMessage.setText(message.getText());

        textMessage.setGuest(new Guest(message.getFrom()));
        textMessage.setChat(new Chat(message.getChat()));

        return textMessageRepository.save(textMessage);
    }

    private void saveTelegramSendMessage(final org.telegram.telegrambots.meta.api.methods.send.SendMessage sendMessage) {

        TextMessage textMessage = new TextMessage();

        textMessage.setDateTime(LocalDateTime.now());
        textMessage.setText(sendMessage.getText());

        Chat chat = new Chat();
        chat.setId(Long.valueOf(sendMessage.getChatId()));

        textMessage.setChat(chat);
        textMessage.setGuest(botAsGuest);
    }
}
