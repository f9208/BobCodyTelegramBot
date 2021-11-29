//package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import ru.bobcody.BobCodyBot;
//import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
//import ru.bobcody.entities.Guest;
//import ru.bobcody.entities.QuoteAbyss;
//import ru.bobcody.services.CapsQuoteStorageService;
//import ru.bobcody.services.GuestService;
//import ru.bobcody.services.QuoteAbyssService;
//import ru.bobcody.services.QuoteStorageService;
//
//import java.util.List;
//@Deprecated
//@Slf4j
//@Setter
//@Getter
////@Component
//public class QuoteAbyssHandler implements SimpleHandlerInterface {
//    @Autowired
//    QuoteAbyssService quoteAbyssService;
//    @Autowired
//    CapsQuoteStorageService capsQuoteStorageService;
//    @Autowired
//    QuoteStorageService quoteStorageService;
//    @Autowired
//    GuestService guestService;
//    @Value("${chatid.admin}")
//    String moderatorChatId;
//    @Autowired
//    BobCodyBot bobCodyBot;
//    @Value("${quote.add.command}")
//    private List<String> commands;
//
//    @Override
//    public SendMessage handle(Message inputMessage) {
//        SendMessage result = new SendMessage();
//        if (inputMessage.getText().trim().startsWith("!добавьк")
//                && moderatorChatId.equals(inputMessage.getChatId().toString())) {
//            result.setText(approveCaps(inputMessage));
//            return result;
//        }
//        if (inputMessage.getText().trim().startsWith("!добавьц")
//                && moderatorChatId.equals(inputMessage.getChatId().toString())) {
//            result.setText(approveQuote(inputMessage));
//            return result;
//        }
//        if (inputMessage.getText().trim().startsWith("!дц")
//                || inputMessage.getText().trim().startsWith("!aq")
//                || inputMessage.getText().trim().startsWith("!lw"))
//            result.setText(addQuoteToAbyss(inputMessage));
//        return result;
//    }
//
//    @Override
//    public List<String> getOrderList() {
//        return commands;
//    }
//
//    private String addQuoteToAbyss(Message message) {
//        String replay;
//        String textQuote;
//        textQuote = message.getText().substring(3);
//        if (textQuote.length() == 0) {
//            replay = message.getFrom().getUserName() + ", ты цитату то введи";
//        } else if (textQuote.length() < 5000) {
//            QuoteAbyss quoteAbyss = new QuoteAbyss(new Guest(message.getFrom()),
//                    Long.valueOf(message.getDate()),
//                    textQuote);
//            quoteAbyssService.add(quoteAbyss);
//            log.info("user {} add quote {}", message.getFrom().getFirstName(), quoteAbyss.getText());
//            replay = "Записал в бездну. Цитата будет добавлена в хранилище после проверки модератором.";
//            sendToModerator(message);
//        } else {
//            replay = "Длина цитаты ограничена 5000 символами. Запиши себе в блокнот, потом поржешь";
//        }
//        return replay;
//    }
//
//    private void sendToModerator(Message message) {
//        log.info("resend message to moderator");
//        String textInputMessage = "пользователь " + message.getFrom().getUserName() +
//                " добавил цитатку. \n" +
//                "ID цитаты в бездне: " + quoteAbyssService.getQuoteIdByDateAdded(message.getDate().longValue()) +
//                "\nText:\n" +
//                message.getText().substring(3);
//        try {
//            SendMessage messageExecute = new SendMessage();
//            messageExecute.setChatId(moderatorChatId);
//            messageExecute.setText(textInputMessage);
//            bobCodyBot.execute(messageExecute);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String approveCaps(Message inputMessage) {
//        String result = "что то пошло не так";
//        String textMessage = inputMessage.getText().toLowerCase();
//        long inputCapsQuoteIdFromAbyss = 0;
//        if (textMessage.split(" ").length == 1) return "чо добавить то?";
//        if (textMessage.split(" ").length == 2) {
//            try {
//                inputCapsQuoteIdFromAbyss = Long.parseLong(textMessage.split(" ")[1]);
//            } catch (NumberFormatException e) {
//                return "цифры вводи.";
//            } catch (ArrayIndexOutOfBoundsException e) {
//                return "ой не влажу в массив";
//            }
//
//            if (!quoteAbyssService.containInAbyss(inputCapsQuoteIdFromAbyss))
//                return "нет такого номера в бездне";
//
//            if (capsQuoteStorageService.containInCapsQuoteStorage(inputCapsQuoteIdFromAbyss)) {
//                return "ее уже добавляли в хранилище Капсов.";
//            }
//            if (quoteStorageService.containInQuoteStorage(inputCapsQuoteIdFromAbyss)) {
//                return "ее уже добавляли в хранилище Цитат.";
//            } else {
//                Long resultNumber = quoteAbyssService.approveCaps(inputCapsQuoteIdFromAbyss);
//                return "Капсик добавлен за номером " + resultNumber;
//            }
//        }
//        log.info("capsQuote with id {} has been approved", inputCapsQuoteIdFromAbyss);
//        return result;
//    }
//
//    private String approveQuote(Message inputMessage) {
//        String result = "что то пошло не так";
//        String textMessage = inputMessage.getText().toLowerCase();
//        long inputQuoteIdFromAbyss = 0;
//
//        if (textMessage.split(" ").length == 1) return "чо добавить то?";
//        if (textMessage.split(" ").length == 2) {
//            try {
//                inputQuoteIdFromAbyss = Long.parseLong(textMessage.split(" ")[1]);
//            } catch (NumberFormatException e) {
//                return "цифры вводи.";
//            } catch (ArrayIndexOutOfBoundsException e) {
//                return "ой не влажу в массив";
//            }
//            if (!quoteAbyssService.containInAbyss(inputQuoteIdFromAbyss))
//                return "нет такого номера в бездне";
//            if (capsQuoteStorageService.containInCapsQuoteStorage(inputQuoteIdFromAbyss)) {
//                return "ее уже добавляли в хранилище Капсов.";
//            }
//            if (quoteStorageService.containInQuoteStorage(inputQuoteIdFromAbyss)) {
//                return "ее уже добавляли в хранилище Цитат.";
//            } else {
//                Long resultNumber = quoteAbyssService.approveQuote(inputQuoteIdFromAbyss);
//                return "цитата добавленна за номером " + resultNumber;
//            }
//        }
//        log.info("quote with id {} has been approved", inputQuoteIdFromAbyss);
//        return result;
//    }
//}