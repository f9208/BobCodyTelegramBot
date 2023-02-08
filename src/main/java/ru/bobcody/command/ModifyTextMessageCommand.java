package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.domain.Chat;
import ru.bobcody.domain.Guest;
import ru.bobcody.domain.TextMessage;
import ru.bobcody.repository.ChatRepository;
import ru.bobcody.repository.GuestRepository;
import ru.bobcody.repository.TextMessageRepository;

import static ru.bobcody.utilits.CommonUtils.*;

public class ModifyTextMessageCommand extends AbstractCommand {
    private final org.telegram.telegrambots.meta.api.objects.Message message;

    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ChatRepository chatRepository;

    public ModifyTextMessageCommand(Message message) {
        this.message = message;
    }

    @Override
    public TextMessage execute() {
        TextMessage textMessage = new TextMessage();

        textMessage.setTelegramId((long) message.getMessageId());
        textMessage.setDateTime(epochToLocalDateTime(message.getDate().longValue()));

        textMessage.setText(message.getText());

        setNotNullAttributes(textMessage, message);

        return textMessageRepository.save(textMessage);
    }

    private void setNotNullAttributes(final TextMessage textMessage,
                                      final org.telegram.telegrambots.meta.api.objects.Message message) {
        final Guest guest =
                guestRepository.findById(message.getFrom().getId())
                        .orElseGet(() -> convertUserToGuest(message.getFrom()));

        textMessage.setGuest(guest);


        final Chat chat = chatRepository.findById(message.getChatId())
                .orElseGet(() -> convertTgChat(message.getChat()));

        textMessage.setChat(chat);
    }
}
