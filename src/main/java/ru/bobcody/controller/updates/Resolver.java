package ru.bobcody.controller.updates;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.resolvers.AnimationMessageResolver;
import ru.bobcody.controller.updates.resolvers.PhotoDocumentMessageResolver;
import ru.bobcody.controller.updates.resolvers.PhotoMessageResolver;
import ru.bobcody.controller.updates.resolvers.TextMessageResolver;

@Component
@RequiredArgsConstructor
public class Resolver {
    private final TextMessageResolver textMessageResolver;
    private final PhotoMessageResolver photoMessageResolver;
    private final PhotoDocumentMessageResolver photoDocumentMessageResolver;
    private final AnimationMessageResolver animationMessageResolver;

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
