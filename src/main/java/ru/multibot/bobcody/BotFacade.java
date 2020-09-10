package ru.multibot.bobcody;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.multibot.bobcody.controller.ChiefHandler;

import java.util.List;

/***
    фасад.
    слой, который обрабатывает update'ы. предполагается, что может обрабатывать еще и callBack'и
    но я не добавлял этот функционал.

    ну и логи еще пишет. но это опционально.

    в листе asf содержатся chatID, для которых есть обработчики.
    соотвтественно, если chatID не содержится в этом листе - входящее сообщение не обрабатывается
 */

@Slf4j
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "chatid")
@PropertySource("classpath:allowedchatid.properties")
public class BotFacade {
    Long ls; //ID моего чата (ЛС) переписки с ботом
    List<Long> asf; // список IDшники чатов, для которых есть обработчики.
    @Autowired
    ChiefHandler chiefHandler;

    public BotApiMethod<?> handleUserUpdate(Update update) {

        BotApiMethod replay = null;
        Message inputMessage = update.getMessage();
        if (inputMessage != null && inputMessage.hasText()) {// chatID 445682905 и -458401902
            log.info("new message from {}, text: \"{}\", chatID: {}, date: {}",
                    inputMessage.getFrom().getUserName(),
                    inputMessage.getText(), inputMessage.getChatId(),
                    inputMessage.getDate());
            replay = handleInputTextMessage(inputMessage);

        } else if (update.hasCallbackQuery()) {
            log.info("new CallBack: {}",
                    update.getCallbackQuery().getData());
            replay = new SendMessage().setChatId(update.getMessage().getChatId()).setText("в колбэках пока не знаю");
        }

        return replay;
    }

//    private BotApiMethod<?> handleCallBackQuery(Update update) {
//        BotApiMethod result = null;
//        CallbackQuery callBack = update.getCallbackQuery();
//        if (callBack.getData().equals("helpCallBack")) {
//
//            lastMessageId = callBack.getMessage().getMessageId();
//            System.out.println("инлайн мессдж Ид из хелпа: " + lastMessageId);
//
//            user.setBobState(BobState.HELP_STATE);
//            result = bobStateContext.processInputMessage(user.getBobState(), callBack.getMessage());
//            user.setBobState(BobState.NON_STATE);
//
//        } else if (callBack.getData().equals("AskWeatherCallBack")) {
//            user.setBobState(BobState.WEATHER_STATE);
//            result = new SendMessage();
//
//            ((SendMessage) result).setChatId(callBack.getMessage().getChatId())
//                    .setText("введите название города: ");
//            ((SendMessage) result).setReplyMarkup(new ReplyKeyboardMain().getKeyboard());
//        }
//
//        if (callBack.getData().equals("alarm")) {
//            result = new EditMessageText()
//                    .setChatId(callBack.getMessage().getChatId())
//                    .setMessageId(lastMessageId)
//                    .setText("все помнеялось");
//            System.out.println(result);
//        }
//        return result;
//    }


    //сделал еще одну прослойку обработчика на всякий случай.
    private SendMessage handleInputTextMessage(Message message) {
        return choiceChat(message);
    }

    private SendMessage choiceChat(Message message) {
        Long chatID = message.getChatId();
        SendMessage replay = null;
        // сделать так, чтобы не было условного оператора по чат-айди, а сам чиф хэндлер сортировал.
        if (chatID.equals(ls)) {
            replay = new SendMessage().setText("ответ на лс сообщение").setChatId(chatID);
        } else if (asf.contains(chatID)) {
            replay = chiefHandler.processInputMessage(message);
        } else replay = new SendMessage().setText("ты кто такой? давай до свидания").setChatId(chatID);

        return replay;
    }
}

