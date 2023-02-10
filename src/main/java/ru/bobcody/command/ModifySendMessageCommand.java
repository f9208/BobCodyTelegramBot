package ru.bobcody.command;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.bobcody.domain.Chat;
import ru.bobcody.domain.Guest;
import ru.bobcody.domain.TextMessage;
import ru.bobcody.repository.ChatRepository;
import ru.bobcody.repository.GuestRepository;
import ru.bobcody.repository.TextMessageRepository;

import java.time.LocalDateTime;

public class ModifySendMessageCommand extends AbstractCommand {
    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ChatRepository chatRepository;
    //todo подумать как сделать по другому. инициализировать запись с ботом при деплое и потом дергать оттуда по ID
    private final Guest botAsGuest = new Guest(0L, "Bob", "Cody", "BobCody", "binary");

    private SendMessage sendMessage;

    public ModifySendMessageCommand(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public TextMessage execute() {
        if (StringUtils.isEmpty(sendMessage.getText())) {
            return new TextMessage();
        }

        TextMessage textMessage = new TextMessage();

        textMessage.setDateTime(LocalDateTime.now());
        textMessage.setText(sendMessage.getText());

        setNotNullAttributes(textMessage);

        return textMessageRepository.save(textMessage);
    }

    private void setNotNullAttributes(TextMessage textMessage) {

        final Chat chat = chatRepository.findById(Long.valueOf(sendMessage.getChatId()))
                .orElseThrow(() -> new RuntimeException("чат не найден"));
        textMessage.setChat(chat);


        final Guest botGuest = guestRepository.findById(botAsGuest.getId())
                .orElseGet(() -> botAsGuest);
        textMessage.setGuest(botGuest);
    }
}
