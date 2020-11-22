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
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
//import ru.multibot.bobcody.controller.ChiefHandler;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.IRCMainHandlerTextMessage;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/***
 фасад.
 слой, который обрабатывает update'ы. предполагается, что может обрабатывать еще и callBack'и
 но я не добавлял этот функционал.

 ну и логи еще пишет. но это опционально.

 в листе addedid(AllovedChatID) содержатся chatID, для которых есть обработчики.
 соотвтественно, если chatID не содержится в этом листе - входящее сообщение не обрабатывается
 */
@Slf4j
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "chatid")
@PropertySource("classpath:allowedchatid.properties")
public class BotFacade {
    List<Long> achid; // список IDшники чатов, для которых есть обработчики.
    //    @Autowired
//    ChiefHandler chiefHandler;
    @Autowired
    IRCMainHandlerTextMessage ircMainHandlerTextMessage;

    public BotApiMethod<?> handleUserUpdate(Update update) {

        BotApiMethod replay = null;
        Message inputMessage = update.getMessage();
        System.out.println("входящее сообщение:"+inputMessage);

        if (inputMessage!=null&&inputMessage.hasAnimation()) {
            System.out.println("анимация");
        }

        if (inputMessage!=null&&inputMessage.hasPhoto()) {
            System.out.println("фото");
        }
        if (inputMessage!=null&&inputMessage.hasDocument()) {
            System.out.println(inputMessage.getDocument().getFileId());
        }

        if(inputMessage!=null&&inputMessage.hasAudio()) {
            System.out.println(inputMessage.getAudio().getFileUniqueId());
            System.out.println(inputMessage.getAudio().getFileId());
        }

        // логирование фоток
        if (inputMessage != null && inputMessage.hasPhoto()) {
            List<PhotoSize> listInputPhoto = inputMessage.getPhoto();
            for (PhotoSize oneSinglePhoto : listInputPhoto) {
                log.info("Input Photo " +
                                " chatID: {}" +
                                " time: {}" +
                                " userName: {}," +
                                " photo: {}", inputMessage.getChatId(),
                        new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(inputMessage.getDate().longValue() * 1000)),
                        inputMessage.getFrom().getUserName(),
                        oneSinglePhoto
                );
            }
        }

        if (inputMessage != null && inputMessage.hasText()) {
            log.info("Input, " +
                            " chatID: {}," +
                            " time: {}," +
                            " userName: {}," +
                            " userId: {}," +
                            " textMessage: \"{}\"",
                    inputMessage.getChatId(),
                    new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(inputMessage.getDate().longValue() * 1000)),
                    inputMessage.getFrom().getUserName(),
                    inputMessage.getFrom().getId(),
                    inputMessage.getText());

            replay = handleInputTextMessage(inputMessage);
            if (((SendMessage) replay).getText() != null) log.info("Output, " +
                            " chatID: {}," +
                            " time: {}," +
                            " userName: BobCody," +
                            " textMessage: \"{}\"",
                    ((SendMessage) replay).getChatId(),
                    LocalTime.now(),
                    ((SendMessage) replay).getText());

        } else if (update!=null&&update.hasCallbackQuery()) {
            log.info("new CallBack: {}",
                    update.getCallbackQuery().getData());
            replay = new SendMessage().setChatId(update.getMessage().getChatId()).setText("в колбэках пока не знаю");
        }
        return replay;
    }

//    private BotApiMethod<?> handleCallBackQuery(Update update) {
//        BotApiMethod result = null;
//        CallbackQuery callBack = update.getCallbackQuery();
//        if (callBack.getDateAdded().equals("helpCallBack")) {
//
//            lastMessageId = callBack.getMessage().getMessageId();
//            System.out.println("инлайн мессдж Ид из хелпа: " + lastMessageId);
//
//            user.setBobState(BobState.HELP_STATE);
//            result = bobStateContext.processInputMessage(user.getBobState(), callBack.getMessage());
//            user.setBobState(BobState.NON_STATE);
//
//        } else if (callBack.getDateAdded().equals("AskWeatherCallBack")) {
//            user.setBobState(BobState.WEATHER_STATE);
//            result = new SendMessage();
//
//            ((SendMessage) result).setChatId(callBack.getMessage().getChatId())
//                    .setText("введите название города: ");
//            ((SendMessage) result).setReplyMarkup(new ReplyKeyboardMain().getKeyboard());
//        }
//
//        if (callBack.getDateAdded().equals("alarm")) {
//            result = new EditMessageText()
//                    .setChatId(callBack.getMessage().getChatId())
//                    .setMessageId(lastMessageId)
//                    .setText("все помнеялось");
//            System.out.println(result);
//        }
//        return result;
//    }

    private SendMessage handleInputTextMessage(Message message) {
        SendMessage replay = new SendMessage();
        try {
            replay = ircMainHandlerTextMessage.handle(message);
        } catch (Exception e) {
            e.printStackTrace();
            replay.setText("чота пошло не так ").setChatId("445682905");
        }
        return replay;
    }
}

