package ru.bobcody.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.BobCodyBot;
import ru.bobcody.services.UpdateReceiverService;

@RestController
public class WebHookController {
    @Autowired
    private BobCodyBot bobCodyBot;

    @Autowired
    private UpdateReceiverService updateReceiverService;

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        BotApiMethod resp=
         updateReceiverService.resolveUpdateType(update);
        return resp;
//        return bobCodyBot.onWebhookUpdateReceived(update);
    }
}

