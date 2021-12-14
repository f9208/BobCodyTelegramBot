package ru.bobcody.controller.updates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.resolvers.AnimationMessageResolver;
import ru.bobcody.controller.updates.resolvers.PhotoDocumentMessageResolver;
import ru.bobcody.controller.updates.resolvers.PhotoMessageResolver;
import ru.bobcody.controller.updates.resolvers.TextMessageResolver;

@Component
public class Resolver {
    @Autowired
    private TextMessageResolver textMessageResolver;
    @Autowired
    private PhotoMessageResolver photoMessageResolver;
    @Autowired
    private PhotoDocumentMessageResolver photoDocumentMessageResolver;
    @Autowired
    private AnimationMessageResolver animationMessageResolver;

    public SendMessage textMessageResolver(Message message, boolean edited) {
        return textMessageResolver.process(message, edited);
    }

    public SendMessage photoMessageResolver(Message message) {
        return photoMessageResolver.process(message);
    }

    public SendMessage photoDocumentMessageResolver(Message message) {
        return photoDocumentMessageResolver.process(message);
    }

    public SendMessage animationMessageResolver(Message message) {
        return animationMessageResolver.process(message);
    }
}
