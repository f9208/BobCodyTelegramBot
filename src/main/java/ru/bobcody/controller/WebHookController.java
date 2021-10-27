package ru.bobcody.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {
    private final BobCodyBot bobCodyBot;

    public WebHookController(BobCodyBot bobCodyBot) {
        this.bobCodyBot = bobCodyBot;
    }

    @PostMapping(value = "/")
    public PartialBotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return bobCodyBot.onWebhookUpdateReceived(update);
    }
}

