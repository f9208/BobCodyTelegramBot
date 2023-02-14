package ru.bobcody.updates.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.ModifyAbyssCommand;
import ru.bobcody.domain.RecordType;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ApproveAbyssHandler extends AbstractHandler {

    private final List<Long> moderators = new ArrayList<>();
    private final Map<String, RecordType> parameterMap = new HashMap<>();

    @PostConstruct
    private void init() {
        moderators.addAll(settingService.getModeratorChatIds());

        parameterMap.put("!approvecaps", RecordType.CAPS);
        parameterMap.put("!approvequote", RecordType.QUOTE);
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        if (!moderators.contains(inputMessage.getFrom().getId())) {
            return new SendMessage();
        }

        SendMessage result = super.handle(inputMessage);

        result.setReplyToMessageId(inputMessage.getMessageId());

        return result;
    }

    @Override
    protected String getResponseTextMessage(Message message) {
        String recordId = getParameterOverDirective(message.getText(), 1);

        RecordType type = recordTypeResolver(message.getText());
        return executeCommand(new ModifyAbyssCommand(recordId, type));
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getRecordApprove();
    }

    private RecordType recordTypeResolver(String text) {
        String directive = getDirective(text);

        if (parameterMap.containsKey(directive)) {
            return parameterMap.get(directive);
        } else {
            throw new RuntimeException("не удалось определить тип команды");
        }
    }
}
