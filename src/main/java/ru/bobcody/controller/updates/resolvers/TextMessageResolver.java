package ru.bobcody.controller.updates.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.bobcody.controller.updates.handlers.chathandlers.MainHandlerTextMessage;
import ru.bobcody.data.entities.Chat;
import ru.bobcody.data.entities.Guest;
import ru.bobcody.data.entities.TextMessage;
import ru.bobcody.data.services.ChatService;
import ru.bobcody.data.services.GuestService;
import ru.bobcody.data.services.TextMessageService;

import java.util.Objects;

import static ru.bobcody.utilits.CommonTextConstant.SMTH_GET_WRONG;

@Component
@RequiredArgsConstructor
public class TextMessageResolver implements IMessageResolver {
    private final MainHandlerTextMessage mainHandlerTextMessage;
    private final GuestService guestService;
    private final TextMessageService textMessageService;
    private final ChatService chatService;

    @Value("${chatid.admin}")
    private String adminChatId;
    private final Guest botAsGuest = new Guest(0L, "Bob", "Cody", "BobCody", "binary");

    public SendMessage process(Message message, boolean edited) {
        SendMessage replay = new SendMessage();
        try {
            saveMessage(message); // бот не отвечает на edited сообщения, но сохраняет их изменения
            if (!edited) {
                replay = process(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            replay.setText(SMTH_GET_WRONG + ": " + e.toString());
            replay.setChatId(adminChatId);
        } finally {
            if (replay.getText()!=null) {
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
        textMessageService.saveOutputMessage(new TextMessage(sendMessage, botAsGuest, messageId, chat));
    }
}