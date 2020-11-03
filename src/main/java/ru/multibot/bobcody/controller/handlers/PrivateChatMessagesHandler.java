package ru.multibot.bobcody.controller.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.QuoteStorageHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@PropertySource(value = {"classpath:devHandlerChatId.properties"})
@ConfigurationProperties(prefix = "devchatid")
public class PrivateChatMessagesHandler implements InputTextMessageHandler {

    List<Long> addedid;
    @Autowired
    QuoteStorageHandler quoteStorageHandler;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage sm = new SendMessage();
        if (inputMessage.hasText()) {
            String textMessage = inputMessage.getText().toLowerCase();
            if (textMessage.startsWith("!добавь")) {
                sm.setText(quoteStorageHandler.approvingQuote(inputMessage));
            }
        }
        sm.setChatId(inputMessage.getChatId());

        return sm;
    }

    @Override
    public List<Long> getChatIDs() {
        List result = new ArrayList();
        for(Long i: addedid) result.add(i);
        return result;
    }
}