package ru.bobcody.controller.resolvers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.entities.Chat;
import ru.bobcody.entities.Guest;
import ru.bobcody.entities.TextMessage;
import ru.bobcody.repository.ChatRepository;
import ru.bobcody.services.GuestService;
import ru.bobcody.services.TextMessageService;

@Getter
@Setter
@Component
public class TextMessageResolver {
    @Autowired
    MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    GuestService guestService;
    @Autowired
    TextMessageService textMessageService;
    @Autowired
    ChatRepository chatRepository;

    public SendMessage process(Message message, boolean edited) {
        SendMessage replay = new SendMessage();
        try {
            saveMessage(message);
            if (!edited) {
                replay = mainHandlerTextMessage.handle(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            replay.setText("что-то пошло не так: " + e.toString()).setChatId("445682905");
        } finally {
            if (replay.getText() != null) {
                saveSendMessage(replay, message.getMessageId(), new Chat(message.getChat()));
            }
        }
        return replay;
    }

    private void saveMessage(Message message) {
        if (message.getFrom() != null) {
            saveGuest(message.getFrom());
        }
        chatRepository.save(new Chat(message.getChat()));
        saveInputMessage(message);
    }

    private void saveGuest(User user) {
        Guest guest = new Guest(user);
        if (!guestService.getAll().contains(guest)) {
            guestService.add(guest);
        }
    }

    private void saveInputMessage(Message message) {
        textMessageService.saveInputMessage(new TextMessage(message));
    }

    private void saveSendMessage(SendMessage sendMessage, int messageId, Chat chat) {
        Guest bot = new Guest(0L, "Bob", "Cody", "BobCody", "binary");
        textMessageService.saveOutputMessage(new TextMessage(sendMessage, bot, messageId, chat));
    }
}