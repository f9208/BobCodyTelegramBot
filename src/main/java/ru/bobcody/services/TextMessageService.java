package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.AbstractCommand;
import ru.bobcody.command.GetInternalDirectiveCommand;
import ru.bobcody.command.ModifySendMessageCommand;
import ru.bobcody.command.ModifyTextMessageCommand;
import ru.bobcody.repository.TextMessageRepository;
import ru.bobcody.updates.handlers.IHandler;
import ru.bobcody.utilits.CommonTextConstant;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TextMessageService implements CommonTextConstant {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private Map<String, IHandler> textMessageHandlers = new HashMap<>();

    @Autowired
    private TextMessageRepository textMessageRepository;

    @Autowired
    private SettingService settingService;

    public TextMessageService(List<IHandler> handlers) {
        for (IHandler iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                textMessageHandlers.put(insideListOrder, iterHandler);
            }
        }
    }

    private void executeCommand(AbstractCommand command) {
        beanFactory.autowireBean(command);
        command.execute();
    }


//    public TextMessage getById(long id) {
//        log.info("try to get messages by number {}", id);
//        return textMessageRepository.findById(id).orElse(null);
//    }

//    public List<TextMessage> getOnDateBetweenForChat(LocalDate start, LocalDate end, long chatId) {
//        log.info("try to get list of messages for dates between {} and {} and chatId {}", start, end, chatId);
//        return textMessageRepository.findAllByDateTimeBetweenAndChatIdOrderByDateTime(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX), chatId);
//    }

//    public List<LocalDate> getListDatesForChat(long chatId) {
//        return textMessageRepository.findAllDateTime(chatId)
//                .stream()
//                .map(Date::toLocalDate)
//                .collect(Collectors.toList());
//    }

    //todo написать на это дело тесты
    public List<String> getTop(long chatId, LocalDateTime since, LocalDateTime to) {
        return textMessageRepository.getTop(chatId, since, to);
    }

    @Transactional
    public SendMessage replyInputMessage(org.telegram.telegrambots.meta.api.objects.Message message, boolean edited) {

        executeCommand(new ModifyTextMessageCommand(message));

        if (edited) {
            return new SendMessage();
        }

        SendMessage sendMessageReply = new SendMessage();

        try {

            sendMessageReply = handle(message);
            sendMessageReply.setChatId(message.getChatId());

        } catch (Exception e) {
            e.printStackTrace();
            sendMessageReply.setText(SMTH_GET_WRONG + ": " + e.toString());
            sendMessageReply.setChatId(settingService.getAdminChatId());
        }

        // Сохраняем ответ, т.к. бот не видит сообщения других ботов, в том числе и свои
        executeCommand(new ModifySendMessageCommand(sendMessageReply));

        return sendMessageReply;
    }

    private SendMessage handle(Message message) {

        GetInternalDirectiveCommand command = new GetInternalDirectiveCommand(message);
        beanFactory.autowireBean(command);
        String internalDirective = command.execute();

        String currentDirective;

        if (StringUtils.isEmpty(internalDirective)) {
            currentDirective = message.getText().toLowerCase().split(" ")[0];

        } else {
            currentDirective = internalDirective;
        }

        if (textMessageHandlers.containsKey(currentDirective)) {

            return textMessageHandlers.get(currentDirective).handle(message);
        }

        return new SendMessage();
    }
}
