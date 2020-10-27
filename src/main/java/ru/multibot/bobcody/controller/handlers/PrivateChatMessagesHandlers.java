//package ru.multibot.bobcody.controller.handlers;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.Hibernate;
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import ru.multibot.bobcody.controller.SQL.Entities.Quote;
//import ru.multibot.bobcody.controller.SQL.Entities.QuoteInsideStorage;
//import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.QuoteStorageHandler;
//
//import java.util.List;
//
//@Component
//@Getter
//@Setter
//@PropertySource(value = {"classpath:allowedchatid.properties"})
//@ConfigurationProperties(prefix = "chatid")
//public class PrivateChatMessagesHandlers implements InputTextMessageHandler {
//
//    List<Long> achid;
//    @Autowired
//    QuoteStorageHandler quoteStorageHandler;
//
//    @Override
//    public SendMessage handle(Message inputMessage) {
//        SendMessage sm = new SendMessage();
//        if (inputMessage.hasText()) {
//            String textMessage = inputMessage.getText().toLowerCase();
//            if (textMessage.startsWith("!добавь")) {
//                sm.setText(quoteStorageHandler.approvingQuote(inputMessage));
//            }
//        }
//        sm.setChatId(inputMessage.getChatId());
//
//        return sm;
//    }
//
//    @Override
//    public Long getChatID() {
//        return achid.get(1);
//    }
//}
