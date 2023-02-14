package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.CommonTextConstant;
import ru.bobcody.command.AbstractCommand;
import ru.bobcody.command.GetInternalDirectiveCommand;
import ru.bobcody.command.ModifySendMessageCommand;
import ru.bobcody.command.ModifyTextMessageCommand;
import ru.bobcody.repository.TextMessageRepository;
import ru.bobcody.updates.handlers.Handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TextMessageService implements CommonTextConstant {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private Map<String, Handler> textMessageHandlers = new HashMap<>();


    public TextMessageService(List<Handler> handlers) {
        for (Handler iterHandler : handlers) {
            for (String insideListOrder : iterHandler.getOrderList()) {
                textMessageHandlers.put(insideListOrder, iterHandler);
            }
        }
    }

    private void executeCommand(AbstractCommand command) {
        beanFactory.autowireBean(command);
        command.execute();
    }

    @Transactional
    public SendMessage replyInputMessage(org.telegram.telegrambots.meta.api.objects.Message message, boolean edited) {

        executeCommand(new ModifyTextMessageCommand(message));

        if (edited) {
            return new SendMessage();
        }

        SendMessage sendMessageReply = handle(message);
        sendMessageReply.setChatId(message.getChatId());

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
