package ru.bobcody.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.resolvers.PhotoDocumentMessageResolver;
import ru.bobcody.controller.resolvers.PhotoMessageResolver;
import ru.bobcody.controller.resolvers.TextMessageResolver;

@Getter
@Setter
@Component
public class Resolver {
    @Autowired
    TextMessageResolver textMessageResolver;
    @Autowired
    PhotoMessageResolver photoMessageResolver;
    @Autowired
    PhotoDocumentMessageResolver photoDocumentMessageResolver;

    public SendMessage textMessageResolver(Message message, boolean edited) {
        return textMessageResolver.process(message, edited);
    }

    public SendMessage photoMessageResolver(Message message) {
        return photoMessageResolver.process(message);
    }

    public SendMessage photoDocumentMessageResolver(Message message) {
        return photoDocumentMessageResolver.process(message);
    }
}
