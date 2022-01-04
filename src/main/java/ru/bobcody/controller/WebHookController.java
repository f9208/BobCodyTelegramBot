package ru.bobcody.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bobcody.BobCodyBot;

import java.io.Serializable;

@RestController
public class WebHookController {
    private final BobCodyBot bobCodyBot;

    public WebHookController(BobCodyBot bobCodyBot) {
        this.bobCodyBot = bobCodyBot;
    }

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return bobCodyBot.onWebhookUpdateReceived(update);
    }
}

