package ru.bobcody.controller.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.bobcody.controller.handlers.chatHandlers.MainHandlerTextMessage;
import ru.bobcody.entities.Chat;
import ru.bobcody.entities.Guest;
import ru.bobcody.entities.TextMessage;
import ru.bobcody.services.ChatService;
import ru.bobcody.services.GuestService;
import ru.bobcody.services.TextMessageService;

import java.util.Objects;

@Component
public class TextMessageResolver extends AbstractMessageResolver {
    @Autowired
    private MainHandlerTextMessage mainHandlerTextMessage;
    @Autowired
    private GuestService guestService;
    @Autowired
    private TextMessageService textMessageService;
    @Autowired
    private ChatService chatService;
    @Value("${chatid.admin}")
    private String chatAdminId;

    public SendMessage process(Message message, boolean edited) {
        SendMessage replay = new SendMessage();
        try {
            saveMessage(message); // бот не отвечает на edited сообщения, но сохраняет их изменения
            if (!edited) {
                replay = process(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            replay.setText("что-то пошло не так: " + e.toString());
            replay.setChatId(chatAdminId);
        } finally {
            if (replay.getText() != null) {
                saveSendMessage(replay, message.getMessageId(), new Chat(message.getChat()));
            }
        }
        return replay;
    }

    public SendMessage process(Message message) {
        return mainHandlerTextMessage.handle(message);
    }

    private void saveMessage(Message message) {
        if (message.getFrom() != null) {
            saveGuest(message.getFrom());
        }
        saveChat(message);
        saveInputMessage(message);
    }

    private Guest saveGuest(User user) {
        Guest guest = new Guest(user);
        if (guestService.containGuest(guest.getId())) {
            if (!guestService.findById(guest.getId()).equals(guest))
                guestService.update(guest);
            return guest;
        } else
            return guestService.add(guest);
    }

    private Chat saveChat(Message message) {
        Objects.requireNonNull(message.getChat());
        Chat chat = new Chat(message.getChat());
        return chatService.containChat(chat.getId()) ? chat : chatService.save(chat);
    }

    private void saveInputMessage(Message message) {
        textMessageService.saveInputMessage(new TextMessage(message));
    }

    private void saveSendMessage(SendMessage sendMessage, int messageId, Chat chat) {
        Guest bot = new Guest(0L, "Bob", "Cody", "BobCody", "binary");
        textMessageService.saveOutputMessage(new TextMessage(sendMessage, bot, messageId, chat));
    }
}