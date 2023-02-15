package ru.bobcody.handler;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.GetRecordCommand;
import ru.bobcody.domain.RecordType;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAskRecord extends AbstractHandler {
    protected final Map<String, RecordType> parameterMap = new HashMap<>();

    @Override
    protected String getResponseTextMessage(Message message) {

        String id = getParameterOverDirective(message.getText(), 1);

        RecordType type = recordTypeResolver(message.getText());

        return executeCommand(new GetRecordCommand(id, type));
    }

    protected RecordType recordTypeResolver(String text) {
        String directive = getDirective(text);

        if (parameterMap.containsKey(directive)) {
            return parameterMap.get(directive);
        } else {
            throw new RuntimeException("не удалось определить тип команды");
        }
    }
}
