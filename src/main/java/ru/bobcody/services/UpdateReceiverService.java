package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.BotFacade;

@Service
public class UpdateReceiverService {
    @Autowired
    private BotFacade botFacade;

    public BotApiMethod<?> resolveUpdateType(Update update) {
        BotApiMethod sendMessage = botFacade.handleUpdate(update);
        return sendMessage;
    }
}
