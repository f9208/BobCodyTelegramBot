package ru.bobcody.updates.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.GetGreatAdviceCommand;

import java.util.List;

@Slf4j
@Component
public class FuckingGreatAdviceHandler extends AbstractHandler implements IHandler {

    @Override
    protected String getResponseTextMessage(Message message) {
        String fgAdvice = executeCommand(new GetGreatAdviceCommand());

        if (message.getFrom().getUserName() == null) {
            return "@" + message.getFrom().getFirstName() + ", " + fgAdvice;
        } else {
            return "@" + message.getFrom().getUserName() + ", " + fgAdvice;
        }
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getFga();
    }
}
