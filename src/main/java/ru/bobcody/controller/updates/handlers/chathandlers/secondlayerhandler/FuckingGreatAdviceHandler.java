package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.IHandler;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.Adviser;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FuckingGreatAdviceHandler implements IHandler {
    @Value("${fga.command}")
    private List<String> commands;
    private final Adviser adviser;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getFrom().getUserName() == null)
            result.setText("@" + inputMessage.getFrom().getFirstName() + ", " + adviser.getAdvice());
        else result.setText("@" + inputMessage.getFrom().getUserName() + ", " + adviser.getAdvice());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
