package ru.bobcody.updates.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.GetGreatAdviceCommand;

import java.util.List;

@Component
public class FuckingGreatAdviceHandler extends AbstractHandler implements Handler {

    @Override
    protected String getResponseTextMessage(Message message) {

        String fgAdvice = executeCommand(new GetGreatAdviceCommand());

        return String.format("@%s , %s",
                getGuestName(message.getFrom()),
                fgAdvice);
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getFga();
    }
}
