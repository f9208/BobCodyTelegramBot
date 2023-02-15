package ru.bobcody.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.CreateAbyssCommand;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;

@Component
public class AddRecordAbyssHandler extends AbstractHandler {

    @Override
    public SendMessage handle(Message inputMessage) {

        SendMessage result = super.handle(inputMessage);

        Integer replyMessageId = inputMessage.getMessageId();
        result.setReplyToMessageId(replyMessageId);

        return result;
    }

    @Override
    protected String getResponseTextMessage(Message message) {

        String textRecord = getParameterOverDirective(message.getText(), MAX_VALUE);

        if (StringUtils.isEmpty(textRecord)) {
            return "саму цитату то введи, дурень";
        }

        if (textRecord.length() > 5000) {
            return "TL;DR, КГ/АМ";
        }

        Long authorId = message.getFrom().getId();

        Long id = executeCommand(new CreateAbyssCommand(authorId, textRecord));

        return String.format(
                "запись добавлена в бездну под номером %d. Она будет проверена модератором",
                id);
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getRecordAdd();
    }
}
